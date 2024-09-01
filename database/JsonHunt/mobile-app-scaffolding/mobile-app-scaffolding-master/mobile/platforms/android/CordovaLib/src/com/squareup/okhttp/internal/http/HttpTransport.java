/*
 * Copyright (C) 2012 The Android Open Source Project
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

package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.internal.AbstractOutputStream;
import com.squareup.okhttp.internal.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.ProtocolException;
import java.net.Socket;

import static com.squareup.okhttp.internal.Util.checkOffsetAndCount;

public final class HttpTransport implements Transport {
  /**
   * The timeout to use while discarding a stream of input data. Since this is
   * used for connection reuse, this timeout should be significantly less than
   * the time it takes to establish a new connection.
   */
  private static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

  public static final int DEFAULT_CHUNK_LENGTH = 1024;

  private final HttpEngine httpEngine;
  private final InputStream socketIn;
  private final OutputStream socketOut;

  /**
   * This stream buffers the request headers and the request body when their
   * combined size is less than MAX_REQUEST_BUFFER_LENGTH. By combining them
   * we can save socket writes, which in turn saves a packet transmission.
   * This is socketOut if the request size is large or unknown.
   */
  private OutputStream requestOut;

  @Override public OutputStream createRequestBody() throws IOException {
    boolean chunked = httpEngine.requestHeaders.isChunked();
    if (!chunked
        && httpEngine.policy.getChunkLength() > 0
        && httpEngine.connection.getHttpMinorVersion() != 0) {
      httpEngine.requestHeaders.setChunked();
      chunked = true;
    }

    // Stream a request body of unknown length.
    if (chunked) {
      int chunkLength = httpEngine.policy.getChunkLength();
      if (chunkLength == -1) {
        chunkLength = DEFAULT_CHUNK_LENGTH;
      }
      writeRequestHeaders();
      return new ChunkedOutputStream(requestOut, chunkLength);
    }

    // Stream a request body of a known length.
    long fixedContentLength = httpEngine.policy.getFixedContentLength();
    if (fixedContentLength != -1) {
      httpEngine.requestHeaders.setContentLength(fixedContentLength);
      writeRequestHeaders();
      return new FixedLengthOutputStream(requestOut, fixedContentLength);
    }

    long contentLength = httpEngine.requestHeaders.getContentLength();
    if (contentLength > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("Use setFixedLengthStreamingMode() or "
          + "setChunkedStreamingMode() for requests larger than 2 GiB.");
    }

    // Buffer a request body of a known length.
    if (contentLength != -1) {
      writeRequestHeaders();
      return new RetryableOutputStream((int) contentLength);
    }

    // Buffer a request body of an unknown length. Don't write request
    // headers until the entire body is ready; otherwise we can't set the
    // Content-Length header correctly.
    return new RetryableOutputStream();
  }

  /** An HTTP body with a fixed length known in advance. */
  private static final class FixedLengthOutputStream extends AbstractOutputStream {
    private final OutputStream socketOut;
    private long bytesRemaining;
  }

  /**
   * An HTTP body with alternating chunk sizes and chunk bodies. Chunks are
   * buffered until {@code maxChunkLength} bytes are ready, at which point the
   * chunk is written and the buffer is cleared.
   */
  private static final class ChunkedOutputStream extends AbstractOutputStream {
    private static final byte[] CRLF = { '\r', '\n' };
    private static final byte[] HEX_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    private static final byte[] FINAL_CHUNK = new byte[] { '0', '\r', '\n', '\r', '\n' };

    /** Scratch space for up to 8 hex digits, and then a constant CRLF. */
    private final byte[] hex = { 0, 0, 0, 0, 0, 0, 0, 0, '\r', '\n' };

    private final OutputStream socketOut;
    private final int maxChunkLength;
    private final ByteArrayOutputStream bufferedChunk;
  }

  /** An HTTP body with a fixed length specified in advance. */
  private static class FixedLengthInputStream extends AbstractHttpInputStream {
    private long bytesRemaining;
  }

  /** An HTTP body with alternating chunk sizes and chunk bodies. */
  private static class ChunkedInputStream extends AbstractHttpInputStream {
    private static final int NO_CHUNK_YET = -1;
    private final HttpTransport transport;
    private int bytesRemainingInChunk = NO_CHUNK_YET;
    private boolean hasMoreChunks = true;

    @Override public int read(byte[] buffer, int offset, int count) throws IOException {
      checkOffsetAndCount(buffer.length, offset, count);
      checkNotClosed();

      if (!hasMoreChunks) {
        return -1;
      }
      if (bytesRemainingInChunk == 0 || bytesRemainingInChunk == NO_CHUNK_YET) {
        readChunkSize();
        if (!hasMoreChunks) {
          return -1;
        }
      }
      int read = in.read(buffer, offset, Math.min(count, bytesRemainingInChunk));
      if (read == -1) {
        unexpectedEndOfInput(); // the server didn't supply the promised chunk length
        throw new IOException("unexpected end of stream");
      }
      bytesRemainingInChunk -= read;
      cacheWrite(buffer, offset, read);
      return read;
    }
  }
}
