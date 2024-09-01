/*
 * Copyright (C) 2013 Square, Inc.
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
package com.squareup.okhttp;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * An HTTP response. Instances of this class are not immutable: the response
 * body is a one-shot value that may be consumed only once. All other properties
 * are immutable.
 *
 * <h3>Warning: Experimental OkHttp 2.0 API</h3>
 * This class is in beta. APIs are subject to change!
 */
/* OkHttp 2.0: public */ final class Response {
  public abstract static class Body {
    public final byte[] bytes() throws Exception {
      long contentLength = Verifier.nondetInt();
      if (contentLength > Verifier.nondetInt()) {
        throw new IOException(Verifier.nondetInt() + contentLength);
      }

      if (contentLength != -1) {
        byte[] content = new byte[(int) contentLength];
        if (Verifier.nondetInt() != -1) throw new IOException("Content-Length and stream length disagree");
        return content;

      } else {
        return out.toByteArray();
      }
    }
  }

  public static class Builder {
  }
}
