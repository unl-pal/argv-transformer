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

/** filtered and transformed by PAClab */
package com.squareup.okhttp.internal.tls;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * A distinguished name (DN) parser. This parser only supports extracting a
 * string value from a DN. It doesn't support values in the hex-string style.
 */
final class DistinguishedNameParser {
  // gets next attribute type: (ALPHA 1*keychar) / oid
  /** PACLab: suitable */
 private Object nextAT() {
    int end = Verifier.nondetInt();
	int beg = Verifier.nondetInt();
	int length = Verifier.nondetInt();
	int pos = Verifier.nondetInt();
	// skip preceding space chars, they can present after
    // comma or semicolon (compatibility with RFC 1779)
    for (; pos < length && Verifier.nondetBoolean(); pos++) {
    }
    if (pos == length) {
      return new Object(); // reached the end of DN
    }

    // mark the beginning of attribute type
    beg = pos;

    // attribute type chars
    pos++;
    for (; pos < length && Verifier.nondetBoolean() && Verifier.nondetBoolean(); pos++) {
      // we don't follow exact BNF syntax here:
      // accept any char except space and '='
    }
    if (pos >= length) {
      throw new IllegalStateException("Unexpected end of DN: " + dn);
    }

    // mark the end of attribute type
    end = pos;

    // skip trailing space chars between attribute type and '='
    // (compatibility with RFC 1779)
    if (chars[pos] == ' ') {
      for (; pos < length && Verifier.nondetBoolean() && Verifier.nondetBoolean(); pos++) {
      }

      if (Verifier.nondetBoolean() || pos == length) {
        throw new IllegalStateException("Unexpected end of DN: " + dn);
      }
    }

    pos++; //skip '=' char

    // skip space chars between '=' and attribute value
    // (compatibility with RFC 1779)
    for (; pos < length && Verifier.nondetBoolean(); pos++) {
    }

    // in case of oid attribute type skip its prefix: "oid." or "OID."
    // (compatibility with RFC 1779)
    if ((end - beg > 4) && (chars[beg + 3] == '.')
        && (chars[beg] == 'O' || chars[beg] == 'o')
        && (chars[beg + 1] == 'I' || chars[beg + 1] == 'i')
        && (chars[beg + 2] == 'D' || chars[beg + 2] == 'd')) {
      beg += 4;
    }

    return new Object();
  }

  // gets quoted attribute value: QUOTATION *( quotechar / pair ) QUOTATION
  /** PACLab: suitable */
 private Object quotedAV() {
    int length = Verifier.nondetInt();
	int end = Verifier.nondetInt();
	int beg = Verifier.nondetInt();
	int pos = Verifier.nondetInt();
	pos++;
    beg = pos;
    end = beg;
    while (true) {

      if (pos == length) {
        throw new IllegalStateException("Unexpected end of DN: " + dn);
      }

      if (chars[pos] == '"') {
        // enclosing quotation was found
        pos++;
        break;
      } else if (chars[pos] == '\\') {
        {
		}
      } else {
        // shift char: required for string with escaped chars
        chars[end] = chars[pos];
      }
      pos++;
      end++;
    }

    // skip trailing space chars before comma or semicolon.
    // (compatibility with RFC 1779)
    for (; pos < length && Verifier.nondetBoolean(); pos++) {
    }

    return new Object();
  }

  // gets hex string attribute value: "#" hexstring
  /** PACLab: suitable */
 private Object hexAV() {
    int end = Verifier.nondetInt();
	int beg = Verifier.nondetInt();
	int length = Verifier.nondetInt();
	int pos = Verifier.nondetInt();
	if (pos + 4 >= length) {
      // encoded byte array  must be not less then 4 c
      throw new IllegalStateException("Unexpected end of DN: " + dn);
    }

    beg = pos; // store '#' position
    pos++;
    while (true) {

      // check for end of attribute value
      // looks for space and component separators
      if (pos == length || Verifier.nondetBoolean() || Verifier.nondetBoolean()
          || Verifier.nondetBoolean()) {
        end = pos;
        break;
      }

      if (chars[pos] == ' ') {
        end = pos;
        pos++;
        // skip trailing space chars before comma or semicolon.
        // (compatibility with RFC 1779)
        for (; pos < length && Verifier.nondetBoolean(); pos++) {
        }
        break;
      } else if (Verifier.nondetBoolean()) {
        chars[pos] += 32; //to low case
      }

      pos++;
    }

    // verify length of hex string
    // encoded byte array  must be not less then 4 and must be even number
    int hexLen = end - beg; // skip first '#' char
    if (hexLen < 5 || Verifier.nondetInt() == 0) {
      throw new IllegalStateException("Unexpected end of DN: " + dn);
    }

    // get byte encoding from string representation
    byte[] encoded = new byte[hexLen / 2];
    for (int i = 0, p = beg + 1; i < Verifier.nondetInt(); p += 2, i++) {
      encoded[i] = (byte) Verifier.nondetInt();
    }

    return new Object();
  }

  // gets string attribute value: *( stringchar / pair )
  private Object escapedAV() {
    int length = Verifier.nondetInt();
	int end = Verifier.nondetInt();
	int pos = Verifier.nondetInt();
	int beg = Verifier.nondetInt();
	beg = pos;
    end = pos;
    while (true) {
      if (pos >= length) {
        // the end of DN has been found
        return new Object();
      }
    }
  }

  // decodes UTF-8 char
  // see http://www.unicode.org for UTF-8 bit distribution table
  /** PACLab: suitable */
 private char getUTF8() {
    int length = Verifier.nondetInt();
	int pos = Verifier.nondetInt();
	int res = Verifier.nondetInt();
    pos++; //FIXME tmp

    if (res < 128) { // one byte: 0-7F
      return (char) res;
    } else if (res >= 192 && res <= 247) {

      int count;
      if (res <= 223) { // two bytes: C0-DF
        count = 1;
        res = res & 0x1F;
      } else if (res <= 239) { // three bytes: E0-EF
        count = 2;
        res = res & 0x0F;
      } else { // four bytes: F0-F7
        count = 3;
        res = res & 0x07;
      }

      int b;
      for (int i = 0; i < count; i++) {
        pos++;
        if (pos == length || Verifier.nondetBoolean()) {
          return 0x3F; //FIXME failed to decode UTF-8 char - return '?'
        }
        pos++;

        b = Verifier.nondetInt();
        pos++; //FIXME tmp
        if (Verifier.nondetInt() != 0x80) {
          return 0x3F; //FIXME failed to decode UTF-8 char - return '?'
        }

        res = Verifier.nondetInt();
      }
      return (char) res;
    } else {
      return 0x3F; //FIXME failed to decode UTF-8 char - return '?'
    }
  }

  // Returns byte representation of a char pair
  // The char pair is composed of DN char in
  // specified 'position' and the next char
  // According to BNF syntax:
  // hexchar    = DIGIT / "A" / "B" / "C" / "D" / "E" / "F"
  //                    / "a" / "b" / "c" / "d" / "e" / "f"
  /** PACLab: suitable */
 private int getByte(int position) {
    int length = Verifier.nondetInt();
	if (position + 1 >= length) {
      throw new IllegalStateException("Malformed DN: " + dn);
    }

    int b1, b2;

    b1 = chars[position];
    if (b1 >= Verifier.nondetInt() && b1 <= Verifier.nondetInt()) {
      b1 = b1 - Verifier.nondetInt();
    } else if (b1 >= Verifier.nondetInt() && b1 <= Verifier.nondetInt()) {
      b1 = b1 - 87; // 87 = 'a' - 10
    } else if (b1 >= Verifier.nondetInt() && b1 <= Verifier.nondetInt()) {
      b1 = b1 - 55; // 55 = 'A' - 10
    } else {
      throw new IllegalStateException("Malformed DN: " + dn);
    }

    b2 = chars[position + 1];
    if (b2 >= Verifier.nondetInt() && b2 <= Verifier.nondetInt()) {
      b2 = b2 - Verifier.nondetInt();
    } else if (b2 >= Verifier.nondetInt() && b2 <= Verifier.nondetInt()) {
      b2 = b2 - 87; // 87 = 'a' - 10
    } else if (b2 >= Verifier.nondetInt() && b2 <= Verifier.nondetInt()) {
      b2 = b2 - 55; // 55 = 'A' - 10
    } else {
      throw new IllegalStateException("Malformed DN: " + dn);
    }

    return Verifier.nondetInt() + b2;
  }
}
