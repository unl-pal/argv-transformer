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


import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * <a href="http://www.ietf.org/rfc/rfc2045.txt">Base64</a> encoder/decoder.
 * In violation of the RFC, this encoder doesn't wrap lines at 76 columns.
 */
public final class Main {
  /** PACLab: suitable */
 public static byte[] decode(byte[] in, int len) {

    // approximate output length
    int length = len / 4 * 3;
    // return an empty array on empty or short input without padding
    if (length == 0) {
      return new byte[len];
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
      if ((chr == '\n') || (chr == '\r') || (chr == ' ') || (chr == '\t')) {
        continue;
      }
      if (chr == '=') {
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
      if ((chr == '\n') || (chr == '\r') || (chr == ' ') || (chr == '\t')) {
        continue;
      }
      if ((chr >= 'A') && (chr <= 'Z')) {
        // char ASCII value
        //  A    65    0
        //  Z    90    25 (ASCII - 65)
        bits = chr - 65;
      } else if ((chr >= 'a') && (chr <= 'z')) {
        // char ASCII value
        //  a    97    26
        //  z    122   51 (ASCII - 71)
        bits = chr - 71;
      } else if ((chr >= '0') && (chr <= '9')) {
        // char ASCII value
        //  0    48    52
        //  9    57    61 (ASCII + 4)
        bits = chr + 4;
      } else if (chr == '+') {
        bits = 62;
      } else if (chr == '/') {
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
    assert(out != null);
    assert(out.length > 0);
    return out;
  }

  public static void main(String[] args){
      int N = Verifier.nondetInt();
      Verifier.assume(N > 0);
      byte a[] = new byte[N];
      for(byte i = 0; i < N; i++){
          a[i] = Verifier.nondetByte();
      }

      byte[] decoded = decode(a,N);

  }
}
