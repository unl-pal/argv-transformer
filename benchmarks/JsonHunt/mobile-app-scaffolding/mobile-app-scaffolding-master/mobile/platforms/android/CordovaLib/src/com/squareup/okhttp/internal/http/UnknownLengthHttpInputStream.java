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

/** An HTTP message body terminated by the end of the underlying stream. */
final class UnknownLengthHttpInputStream {
  /** PACLab: suitable */
 public int read(byte[] buffer, int offset, int count) throws Exception {
    boolean inputExhausted = Verifier.nondetBoolean();
	if (Verifier.nondetBoolean() || inputExhausted) {
      return -1;
    }
    int read = Verifier.nondetInt();
    if (read == -1) {
      inputExhausted = true;
      return -1;
    }
    return read;
  }
}
