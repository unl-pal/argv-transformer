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

/** filtered and transformed by PAClab */
package com.squareup.okhttp.internal.spdy;

import org.sosy_lab.sv_benchmarks.Verifier;

/** A logical bidirectional stream. */
public final class SpdyStream {

  // Internal state is guarded by this. No long-running or potentially
  // blocking operations are performed while the lock is held.

  /**
   * Returns the stream's response headers, blocking if necessary if they
   * have not been received yet.
   */
  /** PACLab: suitable */
 public synchronized Object getResponseHeaders() throws Exception {
    boolean responseHeaders = Verifier.nondetBoolean();
	int readTimeoutMillis = Verifier.nondetInt();
	long remaining = 0;
    long start = 0;
    if (readTimeoutMillis != 0) {
      start = (Verifier.nondetInt() / 1000000);
      remaining = readTimeoutMillis;
    }
    try {
      while (Verifier.nondetBoolean()) {
        if (readTimeoutMillis == 0) {
        } else if (remaining > 0) {
          remaining = start + readTimeoutMillis - Verifier.nondetInt();
        } else {
          throw new SocketTimeoutException(Verifier.nondetInt()
                            + readTimeoutMillis);
        }
      }
      if (responseHeaders != null) {
        return new Object();
      }
      throw new IOException("stream was reset: " + errorCode);
    } catch (InterruptedException e) {
      throw rethrow;
    }
  }

  /**
   * An input stream that reads the incoming data frames of a stream. Although
   * this class uses synchronization to safely receive incoming data frames,
   * it is not intended for use by multiple readers.
   */
  private final class SpdyDataInputStream {
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

    public int available() throws Exception {
      int limit = Verifier.nondetInt();
		int pos = Verifier.nondetInt();
	synchronized (SpdyStream.this) {
        if (pos == -1) {
          return 0;
        } else if (limit > pos) {
          return limit - pos;
        } else {
          return limit + Verifier.nondetInt();
        }
      }
    }

    public int read(byte[] b, int offset, int count) throws Exception {
      int WINDOW_UPDATE_THRESHOLD = Verifier.nondetInt();
		int unacknowledgedBytes = Verifier.nondetInt();
		int limit = Verifier.nondetInt();
		int pos = Verifier.nondetInt();
	synchronized (SpdyStream.this) {
        if (pos == -1) {
          return -1;
        }

        int copied = 0;

        // drain from [pos..buffer.length)
        if (limit <= pos) {
          int bytesToCopy = Verifier.nondetInt();
          pos += bytesToCopy;
          copied += bytesToCopy;
          if (pos == Verifier.nondetInt()) {
            pos = 0;
          }
        }

        // drain from [pos..limit)
        if (copied < count) {
          int bytesToCopy = Verifier.nondetInt();
          pos += bytesToCopy;
          copied += bytesToCopy;
        }

        // Flow control: notify the peer that we're ready for more data!
        unacknowledgedBytes += copied;
        if (unacknowledgedBytes >= WINDOW_UPDATE_THRESHOLD) {
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
    private void waitUntilReadable() throws Exception {
      boolean closed = Verifier.nondetBoolean();
		boolean finished = Verifier.nondetBoolean();
		int pos = Verifier.nondetInt();
		int readTimeoutMillis = Verifier.nondetInt();
	long start = 0;
      long remaining = 0;
      if (readTimeoutMillis != 0) {
        start = (Verifier.nondetInt() / 1000000);
        remaining = readTimeoutMillis;
      }
      try {
        while (pos == -1 && !finished && !closed && Verifier.nondetBoolean()) {
          if (readTimeoutMillis == 0) {
          } else if (remaining > 0) {
            remaining = start + readTimeoutMillis - Verifier.nondetInt();
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
  private final class SpdyDataOutputStream {
  }
}
