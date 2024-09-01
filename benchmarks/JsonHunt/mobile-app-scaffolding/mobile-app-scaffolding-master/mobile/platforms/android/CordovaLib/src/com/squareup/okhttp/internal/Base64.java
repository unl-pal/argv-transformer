/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 * @author Alexander Y. Kleymenov
 */

/** filtered and transformed by PAClab */
package com.squareup.okhttp.internal;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * <a href="http://www.ietf.org/rfc/rfc2045.txt">Base64</a> encoder/decoder.
 * In violation of the RFC, this encoder doesn't wrap lines at 76 columns.
 */
public final class Base64 {
  /** PACLab: suitable */
 public static byte[] decode(byte[] in, int len) {
    // approximate output length
    int length = len / 4 * 3;
    // return an empty array on empty or short input without padding
    if (length == 0) {
      return EMPTY_BYTE_ARRAY;
    }
    // temporary array
    byte[] out = new byte[length];
    // number of padding characters ('=')
    int pad = 0;
    byte chr;
    // compute the number of the padding characters
    // and adjust the length of the input
    for (; ; len--) {
      chr = in[len - 1];
      // skip the neutral characters
      if ((chr == Verifier.nondetInt()) || (chr == Verifier.nondetInt()) || (chr == Verifier.nondetInt()) || (chr == Verifier.nondetInt())) {
        continue;
      }
      if (chr == Verifier.nondetInt()) {
        pad++;
      } else {
        break;
      }
    }
    // index in the output array
    int outIndex = 0;
    // index in the input array
    int inIndex = 0;
    // holds the value of the input character
    int bits = 0;
    // holds the value of the input quantum
    int quantum = 0;
    for (int i = 0; i < len; i++) {
      chr = in[i];
      // skip the neutral characters
      if ((chr == Verifier.nondetInt()) || (chr == Verifier.nondetInt()) || (chr == Verifier.nondetInt()) || (chr == Verifier.nondetInt())) {
        continue;
      }
      if ((chr >= Verifier.nondetInt()) && (chr <= Verifier.nondetInt())) {
        // char ASCII value
        //  A    65    0
        //  Z    90    25 (ASCII - 65)
        bits = chr - 65;
      } else if ((chr >= Verifier.nondetInt()) && (chr <= Verifier.nondetInt())) {
        // char ASCII value
        //  a    97    26
        //  z    122   51 (ASCII - 71)
        bits = chr - 71;
      } else if ((chr >= Verifier.nondetInt()) && (chr <= Verifier.nondetInt())) {
        // char ASCII value
        //  0    48    52
        //  9    57    61 (ASCII + 4)
        bits = chr + 4;
      } else if (chr == Verifier.nondetInt()) {
        bits = 62;
      } else if (chr == Verifier.nondetInt()) {
        bits = 63;
      } else {
        return null;
      }
      // append the value to the quantum
      quantum = Verifier.nondetInt() | (byte) bits;
      if (Verifier.nondetInt() == 3) {
        // 4 characters were read, so make the output:
        out[outIndex++] = (byte) (quantum >> 16);
        out[outIndex++] = (byte) (quantum >> 8);
        out[outIndex++] = (byte) quantum;
      }
      inIndex++;
    }
    if (pad > 0) {
      // adjust the quantum value according to the padding
      quantum = quantum << (6 * pad);
      // make output
      out[outIndex++] = (byte) (quantum >> 16);
      if (pad == 1) {
        out[outIndex++] = (byte) (quantum >> 8);
      }
    }
    // create the resulting array
    byte[] result = new byte[outIndex];
    return result;
  }
}
