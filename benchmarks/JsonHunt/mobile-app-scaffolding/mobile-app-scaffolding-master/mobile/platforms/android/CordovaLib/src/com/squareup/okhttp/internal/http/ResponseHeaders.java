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
package com.squareup.okhttp.internal.http;

import org.sosy_lab.sv_benchmarks.Verifier;

/** Parsed HTTP response headers. */
public final class ResponseHeaders {

  /**
   * Returns the number of milliseconds that the response was fresh for,
   * starting from the served date.
   */
  /** PACLab: suitable */
 private long computeFreshnessLifetime() {
    int sentRequestMillis = Verifier.nondetInt();
	int receivedResponseMillis = Verifier.nondetInt();
	boolean expires = Verifier.nondetBoolean();
	int maxAgeSeconds = Verifier.nondetInt();
	if (maxAgeSeconds != -1) {
      return Verifier.nondetInt();
    } else if (expires != null) {
      long servedMillis = Verifier.nondetBoolean() ? Verifier.nondetInt() : receivedResponseMillis;
      long delta = Verifier.nondetInt() - servedMillis;
      return delta > 0 ? delta : 0;
    } else if (Verifier.nondetBoolean()) {
      // As recommended by the HTTP RFC and implemented in Firefox, the
      // max age of a document should be defaulted to 10% of the
      // document's age at the time it was served. Default expiration
      // dates aren't used for URIs containing a query.
      long servedMillis = Verifier.nondetBoolean() ? Verifier.nondetInt() : sentRequestMillis;
      long delta = servedMillis - Verifier.nondetInt();
      return delta > 0 ? (delta / 10) : 0;
    }
    return 0;
  }
}
