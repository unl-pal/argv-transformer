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


import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * A distinguished name (DN) parser. This parser only supports extracting a
 * string value from a DN. It doesn't support values in the hex-string style.
 */
public class Main {


      // gets next attribute type: (ALPHA 1*keychar) / oid
      /** PACLab: suitable */

     public static Object nextAT() {
         int pos = Verifier.nondetInt();
         int length = Verifier.nondetInt();

         if(length < 0 && pos >= length){
             return null;
         }
         assert (length >= 0);
         assert (pos < length);

         char[] chars = new char[length];

         for (int i = 0; i < length; i++) {
             chars[i] = (char) (Verifier.nondetInt() % 95 + 32); // Random printable ASCII characters
         }

        // skip preceding space chars, they can present after
        // comma or semicolon (compatibility with RFC 1779)
         // Skip preceding space characters
         while (pos < length && chars[pos] == ' ') { // as per original program
             pos++;
         }

         //position should be within the bounds
         if(pos >= length) {
             return null;
         }


        // mark the beginning of attribute type
         int beg = pos;
         pos++;
         while (pos < length && chars[pos] != ' ' && chars[pos] != '=') { // as per original code conditions are getting checked
             pos++;
         }


        if (pos >= length) {
            throw new IllegalStateException("Unexpected end of DN: ");
        }
        // mark the end of attribute type
        int end = pos;


//         skip trailing space chars between attribute type and '='
//         (compatibility with RFC 1779)
        if (chars[pos] == ' ') {
            while (pos < length && chars[pos] == ' ') {
                pos++;
            }
            if (Verifier.nondetBoolean() || pos == length) {
                throw new IllegalStateException("Unexpected end of DN: " );
            }
        }
        pos++; //skip '=' char

        // skip space chars between '=' and attribute value
        // (compatibility with RFC 1779)
         while (pos < length && chars[pos] == ' ') {
             pos++;
         }

         assert (pos >= length);

        // in case of oid attribute type skip its prefix: "oid." or "OID."
        // (compatibility with RFC 1779)
        if ((end - beg > 4) && (chars[beg + 3] == '.')
            && (chars[beg] == 'O' || chars[beg] == 'o')
            && (chars[beg + 1] == 'I' || chars[beg + 1] == 'i')
            && (chars[beg + 2] == 'D' || chars[beg + 2] == 'd')) {
          beg += 4;
//
        }
        assert (beg >= end);

        return new String(chars, beg, end - beg);
     }

     public static void main(String[] args){
         nextAT();
     }
}
