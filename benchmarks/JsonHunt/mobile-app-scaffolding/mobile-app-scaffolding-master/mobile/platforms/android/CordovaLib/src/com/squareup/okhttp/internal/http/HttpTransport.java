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

/** filtered and transformed by PAClab */
package com.squareup.okhttp.internal.http;

import org.sosy_lab.sv_benchmarks.Verifier;

public final class HttpTransport {
  /** PACLab: suitable */
 public Object createRequestBody() throws Exception {
    int DEFAULT_CHUNK_LENGTH = Verifier.nondetInt();
	boolean chunked = rand.nextBoolean();
    if (!chunked
        && Verifier.nondetInt() > 0
        && Verifier.nondetInt() != 0) {
      chunked = true;
    }

    // Stream a request body of unknown length.
    if (chunked) {
      int chunkLength = Verifier.nondetInt();
      if (chunkLength == -1) {
        chunkLength = DEFAULT_CHUNK_LENGTH;
      }
      return new Object();
    }

    // Stream a request body of a known length.
    long fixedContentLength = Verifier.nondetInt();
    if (fixedContentLength != -1) {
      return new Object();
    }

    long contentLength = Verifier.nondetInt();
    if (contentLength > Verifier.nondetInt()) {
      throw new IllegalArgumentException("Use setFixedLengthStreamingMode() or "
          + "setChunkedStreamingMode() for requests larger than 2 GiB.");
    }

    // Buffer a request body of a known length.
    if (contentLength != -1) {
      return new Object();
    }

    // Buffer a request body of an unknown length. Don't write request
    // headers until the entire body is ready; otherwise we can't set the
    // Content-Length header correctly.
    return new Object();
  }

  /** An HTTP body with a fixed length known in advance. */
  private static final class FixedLengthOutputStream {
  }

  /**
   * An HTTP body with alternating chunk sizes and chunk bodies. Chunks are
   * buffered until {@code maxChunkLength} bytes are ready, at which point the
   * chunk is written and the buffer is cleared.
   */
  private static final class ChunkedOutputStream {
  }

  /** An HTTP body with a fixed length specified in advance. */
  private static class FixedLengthInputStream {
  }

  /** An HTTP body with alternating chunk sizes and chunk bodies. */
  private static class ChunkedInputStream {
    public int read(byte[] buffer, int offset, int count) throws Exception {
      int NO_CHUNK_YET = Verifier.nondetInt();
		int bytesRemainingInChunk = Verifier.nondetInt();
		boolean hasMoreChunks = rand.nextBoolean();
	if (!hasMoreChunks) {
        return -1;
      }
      if (bytesRemainingInChunk == 0 || bytesRemainingInChunk == NO_CHUNK_YET) {
        if (!hasMoreChunks) {
          return -1;
        }
      }
      int read = Verifier.nondetInt();
      if (read == -1) {
        throw new IOException("unexpected end of stream");
      }
      bytesRemainingInChunk -= read;
      return read;
    }
  }
}
