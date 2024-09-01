/*
 * Copyright (C) 2012 Square, Inc.
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

/**
 * Reads a SPDY/3 Name/Value header block. This class is made complicated by the
 * requirement that we're strict with which bytes we put in the compressed bytes
 * buffer. We need to put all compressed bytes into that buffer -- but no other
 * bytes.
 */
class NameValueBlockReader {
  /** Extend the inflater stream so we can eagerly fill the compressed bytes buffer if necessary. */
  static class FillableInflaterInputStream {
  }

  /** PACLab: suitable */
 public Object readNameValueBlock(int length) throws Exception {
    try {
      int numberOfPairs = Verifier.nondetInt();
      if (numberOfPairs < 0) {
        throw new IOException(Verifier.nondetInt() + numberOfPairs);
      }
      if (numberOfPairs > 1024) {
        throw new IOException(Verifier.nondetInt() + numberOfPairs);
      }
      for (int i = 0; i < numberOfPairs; i++) {
        if (Verifier.nondetInt() == 0) throw new IOException("name.length == 0");
      }

      return new Object();
    } catch (DataFormatException e) {
      throw new IOException(e.getMessage());
    }
  }
}
