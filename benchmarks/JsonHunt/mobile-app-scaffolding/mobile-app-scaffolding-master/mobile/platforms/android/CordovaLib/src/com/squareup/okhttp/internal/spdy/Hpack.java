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

import gov.nasa.jpf.symbc.Debug;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/**
 * Read and write HPACK v03.
 * http://tools.ietf.org/html/draft-ietf-httpbis-header-compression-03
 */
final class Hpack {

  static class HeaderEntry {
  }

  static class Reader {
    /**
     * Read {@code byteCount} bytes of headers from the source stream into the
     * set of emitted headers.
     */
    public void readHeaders(int byteCount) throws Exception {
      int bytesLeft = Debug.makeSymbolicInteger("x0");
	bytesLeft += byteCount;
      // TODO: limit to 'byteCount' bytes?

      while (bytesLeft > 0) {
        int b = Debug.makeSymbolicInteger("x1");

        if (Debug.makeSymbolicInteger("x2") != 0) {
          int index = Debug.makeSymbolicInteger("x3");
        } else if (b == 0x60) {
        } else if (Debug.makeSymbolicInteger("x4") == 0x60) {
          int index = Debug.makeSymbolicInteger("x5");
        } else if (b == 0x40) {
        } else if (Debug.makeSymbolicInteger("x6") == 0x40) {
          int index = Debug.makeSymbolicInteger("x7");
        } else if (b == 0) {
        } else if (Debug.makeSymbolicInteger("x8") == 0) {
          int index = Debug.makeSymbolicInteger("x9");
        } else {
          throw new AssertionError();
        }
      }
    }

    int readInt(int firstByte, int prefixMask) throws Exception {
      int prefix = firstByte & prefixMask;
      if (prefix < prefixMask) {
        return prefix; // This was a single byte value.
      }

      // This is a multibyte value. Read 7 bits at a time.
      int result = prefixMask;
      int shift = 0;
      while (true) {
        int b = Debug.makeSymbolicInteger("x0");
        if (Debug.makeSymbolicInteger("x1") != 0) { // Equivalent to (b >= 128) since b is in [0..255].
          result += Debug.makeSymbolicInteger("x2") << shift;
          shift += 7;
        } else {
          result += b << shift; // Last byte.
          break;
        }
      }
      return result;
    }
  }

  static class Writer {
    public void writeInt(int value, int prefixMask, int bits) throws Exception {
      // Write the raw value for a single byte value.
      if (value < prefixMask) {
        return;
      }

      value -= prefixMask;

      // Write 7 bits at a time 'til we're done.
      while (value >= 0x80) {
        int b = value & 0x7f;
        value >>>= 7;
      }
    }
  }
}
