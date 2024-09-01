/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static com.squareup.okhttp.internal.Util.checkOffsetAndCount;

/** A logical bidirectional stream. */
public final class SpdyStream {

  // Internal state is guarded by this. No long-running or potentially
  // blocking operations are performed while the lock is held.

  /**
   * The number of unacknowledged bytes at which the input stream will send
   * the peer a {@code WINDOW_UPDATE} frame. Must be less than this client's
   * window size, otherwise the remote peer will stop sending data on this
   * stream. (Chrome 25 uses 5 MiB.)
   */
  public static final int WINDOW_UPDATE_THRESHOLD = Settings.DEFAULT_INITIAL_WINDOW_SIZE / 2;

  private final int id;
  private final SpdyConnection connection;
  private final int priority;
  private long readTimeoutMillis = 0;
  private int writeWindowSize;

  /** Headers sent by the stream initiator. Immutable and non null. */
  private final List<String> requestHeaders;

  /** Headers sent in the stream reply. Null if reply is either not sent or not sent yet. */
  private List<String> responseHeaders;

  private final SpdyDataInputStream in = new SpdyDataInputStream();
  private final SpdyDataOutputStream out = new SpdyDataOutputStream();

  /**
   * The reason why this stream was abnormally closed. If there are multiple
   * reasons to abnormally close this stream (such as both peers closing it
   * near-simultaneously) then this is the first reason known to this peer.
   */
  private ErrorCode errorCode = null;

  /**
   * Returns the stream's response headers, blocking if necessary if they
   * have not been received yet.
   */
  public synchronized List<String> getResponseHeaders() throws IOException {
    long remaining = 0;
    long start = 0;
    if (readTimeoutMillis != 0) {
      start = (System.nanoTime() / 1000000);
      remaining = readTimeoutMillis;
    }
    try {
      while (responseHeaders == null && errorCode == null) {
        if (readTimeoutMillis == 0) { // No timeout configured.
          wait();
        } else if (remaining > 0) {
          wait(remaining);
          remaining = start + readTimeoutMillis - (System.nanoTime() / 1000000);
        } else {
          throw new SocketTimeoutException("Read response header timeout. readTimeoutMillis: "
                            + readTimeoutMillis);
        }
      }
      if (responseHeaders != null) {
        return responseHeaders;
      }
      throw new IOException("stream was reset: " + errorCode);
    } catch (InterruptedException e) {
      InterruptedIOException rethrow = new InterruptedIOException();
      rethrow.initCause(e);
      throw rethrow;
    }
  }

  /**
   * An input stream that reads the incoming data frames of a stream. Although
   * this class uses synchronization to safely receive incoming data frames,
   * it is not intended for use by multiple readers.
   */
  private final class SpdyDataInputStream extends InputStream {
    // Store incoming data bytes in a circular buffer. When the buffer is
    // empty, pos == -1. Otherwise pos is the first byte to read and limit
    // is the first byte to write.
    //
    // { - - - X X X X - - - }
    //         ^       ^
    //        pos    limit
    //
    // { X X X - - - - X X X }
    //         ^       ^
    //       limit    pos

    private final byte[] buffer = new byte[Settings.DEFAULT_INITIAL_WINDOW_SIZE];

    /** the next byte to be read, or -1 if the buffer is empty. Never buffer.length */
    private int pos = -1;

    /** the last byte to be read. Never buffer.length */
    private int limit;

    /** True if the caller has closed this stream. */
    private boolean closed;

    /**
     * True if either side has cleanly shut down this stream. We will
     * receive no more bytes beyond those already in the buffer.
     */
    private boolean finished;

    /**
     * The total number of bytes consumed by the application (with {@link
     * #read}), but not yet acknowledged by sending a {@code WINDOW_UPDATE}
     * frame.
     */
    private int unacknowledgedBytes = 0;

    @Override public int available() throws IOException {
      synchronized (SpdyStream.this) {
        checkNotClosed();
        if (pos == -1) {
          return 0;
        } else if (limit > pos) {
          return limit - pos;
        } else {
          return limit + (buffer.length - pos);
        }
      }
    }

    @Override public int read(byte[] b, int offset, int count) throws IOException {
      synchronized (SpdyStream.this) {
        checkOffsetAndCount(b.length, offset, count);
        waitUntilReadable();
        checkNotClosed();

        if (pos == -1) {
          return -1;
        }

        int copied = 0;

        // drain from [pos..buffer.length)
        if (limit <= pos) {
          int bytesToCopy = Math.min(count, buffer.length - pos);
          System.arraycopy(buffer, pos, b, offset, bytesToCopy);
          pos += bytesToCopy;
          copied += bytesToCopy;
          if (pos == buffer.length) {
            pos = 0;
          }
        }

        // drain from [pos..limit)
        if (copied < count) {
          int bytesToCopy = Math.min(limit - pos, count - copied);
          System.arraycopy(buffer, pos, b, offset + copied, bytesToCopy);
          pos += bytesToCopy;
          copied += bytesToCopy;
        }

        // Flow control: notify the peer that we're ready for more data!
        unacknowledgedBytes += copied;
        if (unacknowledgedBytes >= WINDOW_UPDATE_THRESHOLD) {
          connection.writeWindowUpdateLater(id, unacknowledgedBytes);
          unacknowledgedBytes = 0;
        }

        if (pos == limit) {
          pos = -1;
          limit = 0;
        }

        return copied;
      }
    }

    /**
     * Returns once the input stream is either readable or finished. Throws
     * a {@link SocketTimeoutException} if the read timeout elapses before
     * that happens.
     */
    private void waitUntilReadable() throws IOException {
      long start = 0;
      long remaining = 0;
      if (readTimeoutMillis != 0) {
        start = (System.nanoTime() / 1000000);
        remaining = readTimeoutMillis;
      }
      try {
        while (pos == -1 && !finished && !closed && errorCode == null) {
          if (readTimeoutMillis == 0) {
            SpdyStream.this.wait();
          } else if (remaining > 0) {
            SpdyStream.this.wait(remaining);
            remaining = start + readTimeoutMillis - (System.nanoTime() / 1000000);
          } else {
            throw new SocketTimeoutException();
          }
        }
      } catch (InterruptedException e) {
        throw new InterruptedIOException();
      }
    }
  }

  /**
   * An output stream that writes outgoing data frames of a stream. This class
   * is not thread safe.
   */
  private final class SpdyDataOutputStream extends OutputStream {
    private final byte[] buffer = new byte[8192];
    private int pos = 0;

    /** True if the caller has closed this stream. */
    private boolean closed;

    /**
     * True if either side has cleanly shut down this stream. We shall send
     * no more bytes.
     */
    private boolean finished;

    /**
     * The total number of bytes written out to the peer, but not yet
     * acknowledged with an incoming {@code WINDOW_UPDATE} frame. Writes
     * block if they cause this to exceed the {@code WINDOW_SIZE}.
     */
    private int unacknowledgedBytes = 0;
  }
}
