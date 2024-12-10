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


import org.sosy_lab.sv_benchmarks.Verifier;

public final class Main {
  /** PACLab: suitable */
    public static Object createRequestBody() throws Exception {
        int defaultChunkLength = Verifier.nondetInt();
        boolean chunked = Verifier.nondetBoolean();
        if (!chunked
            && Verifier.nondetInt() > 0
            && Verifier.nondetInt() != 0) {
          chunked = true;
        }

        // Stream a request body of unknown length.
        if (chunked) {
          int chunkLength = Verifier.nondetInt();
          if (chunkLength == -1) {
            chunkLength = defaultChunkLength;
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

    public static void main(String[] args){
        try{
            Object object = createRequestBody();
            assert(object != null);
        } catch (Exception e){
            assert(e.getMessage().equals("Use setFixedLengthStreamingMode() or "
                    + "setChunkedStreamingMode() for requests larger than 2 GiB."));
        }


    }


}
