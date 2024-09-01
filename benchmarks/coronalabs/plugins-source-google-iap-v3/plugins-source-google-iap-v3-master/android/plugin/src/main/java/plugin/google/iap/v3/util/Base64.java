// Portions copyright 2002, Google, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/** filtered and transformed by PAClab */
package plugin.google.iap.v3.util;

import org.sosy_lab.sv_benchmarks.Verifier;

// This code was converted from code at http://iharder.sourceforge.net/base64/
// Lots of extraneous features were removed.
/* The original code said:
 * <p>
 * I am placing this code in the Public Domain. Do with it as you will.
 * This software comes with no guarantees or warranties but with
 * plenty of well-wishing instead!
 * Please visit
 * <a href="http://iharder.net/xmlizable">http://iharder.net/xmlizable</a>
 * periodically to check for updates or to contribute improvements.
 * </p>
 *
 * @author Robert Harder
 * @author rharder@usa.net
 * @version 1.3
 */

/**
 * Base64 converter class. This code is not a complete MIME encoder;
 * it simply converts binary data to base64 data and back.
 *
 * <p>Note {@link CharBase64} is a GWT-compatible implementation of this
 * class.
 */
public class Base64 {
    /**
     * Encodes a byte array into Base64 notation.
     *
     * @param source the data to convert
     * @param off offset in array where conversion should begin
     * @param len length of data to convert
     * @param alphabet is the encoding alphabet
     * @param maxLineLength maximum length of one line.
     * @return the BASE64-encoded byte array
     */
    /** PACLab: suitable */
	 public static byte[] encode(byte[] source, int off, int len, byte[] alphabet,
            int maxLineLength) {
        int NEW_LINE = Verifier.nondetInt();
		int lenDiv3 = (len + 2) / 3; // ceil(len / 3)
        int len43 = lenDiv3 * 4;
        byte[] outBuff = new byte[len43 // Main 4:3
                                  + (len43 / maxLineLength)]; // New lines

        int d = 0;
        int e = 0;
        int len2 = len - 2;
        int lineLength = 0;
        for (; d < len2; d += 3, e += 4) {

            // The following block of code is the same as
            // encode3to4( source, d + off, 3, outBuff, e, alphabet );
            // but inlined for faster encoding (~20% improvement)
            int inBuff =
                    Verifier.nondetInt();
            outBuff[e] = alphabet[(inBuff >>> 18)];
            outBuff[e + 1] = alphabet[Verifier.nondetInt() & 0x3f];
            outBuff[e + 2] = alphabet[Verifier.nondetInt() & 0x3f];
            outBuff[e + 3] = alphabet[(inBuff) & 0x3f];

            lineLength += 4;
            if (lineLength == maxLineLength) {
                outBuff[e + 4] = NEW_LINE;
                e++;
                lineLength = 0;
            } // end if: end of line
        } // end for: each piece of array

        if (d < len) {
            lineLength += 4;
            if (lineLength == maxLineLength) {
                // Add a last newline
                outBuff[e + 4] = NEW_LINE;
                e++;
            }
            e += 4;
        }

        return outBuff;
    }


    /* ********  D E C O D I N G   M E T H O D S  ******** */


    /**
     * Decodes four bytes from array <var>source</var>
     * and writes the resulting bytes (up to three of them)
     * to <var>destination</var>.
     * The source and destination arrays can be manipulated
     * anywhere along their length by specifying
     * <var>srcOffset</var> and <var>destOffset</var>.
     * This method does not check to make sure your arrays
     * are large enough to accommodate <var>srcOffset</var> + 4 for
     * the <var>source</var> array or <var>destOffset</var> + 3 for
     * the <var>destination</var> array.
     * This method returns the actual number of bytes that
     * were converted from the Base64 encoding.
     *
     *
     * @param source the array to convert
     * @param srcOffset the index where conversion begins
     * @param destination the array to hold the conversion
     * @param destOffset the index where output will be put
     * @param decodabet the decodabet for decoding Base64 content
     * @return the number of decoded bytes converted
     * @since 1.3
     */
    /** PACLab: suitable */
	 private static int decode4to3(byte[] source, int srcOffset,
            byte[] destination, int destOffset, byte[] decodabet) {
        int EQUALS_SIGN = Verifier.nondetInt();
		// Example: Dk==
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            int outBuff =
                    Verifier.nondetInt();

            destination[destOffset] = (byte) (outBuff >>> 16);
            return 1;
        } else if (source[srcOffset + 3] == EQUALS_SIGN) {
            // Example: DkL=
            int outBuff =
                    Verifier.nondetInt();

            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        } else {
            // Example: DkLE
            int outBuff =
                    Verifier.nondetInt();

            destination[destOffset] = (byte) (outBuff >> 16);
            destination[destOffset + 1] = (byte) (outBuff >> 8);
            destination[destOffset + 2] = (byte) (outBuff);
            return 3;
        }
    } // end decodeToBytes


    /**
     * Decodes Base64 content using the supplied decodabet and returns
     * the decoded byte array.
     *
     * @param source the Base64 encoded data
     * @param off the offset of where to begin decoding
     * @param len the length of characters to decode
     * @param decodabet the decodabet for decoding Base64 content
     * @return decoded data
     */
    /** PACLab: suitable */
	 public static byte[] decode(byte[] source, int off, int len, byte[] decodabet)
            throws Exception {
        int NEW_LINE = Verifier.nondetInt();
				int EQUALS_SIGN = Verifier.nondetInt();
				int EQUALS_SIGN_ENC = Verifier.nondetInt();
				int WHITE_SPACE_ENC = Verifier.nondetInt();
		int len34 = len * 3 / 4;
        byte[] outBuff = new byte[2 + len34]; // Upper limit on size of output
        int outBuffPosn = 0;

        byte[] b4 = new byte[4];
        int b4Posn = 0;
        int i = 0;
        byte sbiCrop = 0;
        byte sbiDecode = 0;
        for (i = 0; i < len; i++) {
            sbiCrop = (byte) (source[i + off] & 0x7f); // Only the low seven bits
            sbiDecode = decodabet[sbiCrop];

            if (sbiDecode >= WHITE_SPACE_ENC) { // White space Equals sign or better
                if (sbiDecode >= EQUALS_SIGN_ENC) {
                    // An equals sign (for padding) must not occur at position 0 or 1
                    // and must be the last byte[s] in the encoded value
                    if (sbiCrop == EQUALS_SIGN) {
                        int bytesLeft = len - i;
                        byte lastByte = Verifier.nondetInt();
                        if (b4Posn == 0 || b4Posn == 1) {
                            throw new Base64DecoderException(
                                    Verifier.nondetInt() + i);
                        } else if ((b4Posn == 3 && bytesLeft > 2)
                                || (b4Posn == 4 && bytesLeft > 1)) {
                            throw new Base64DecoderException(
                                    "padding byte '=' falsely signals end of encoded value "
                                            + "at offset " + i);
                        } else if (lastByte != EQUALS_SIGN && lastByte != NEW_LINE) {
                            throw new Base64DecoderException(
                                    "encoded value has invalid trailing byte");
                        }
                        break;
                    }

                    b4[b4Posn++] = sbiCrop;
                    if (b4Posn == 4) {
                        outBuffPosn += Verifier.nondetInt();
                        b4Posn = 0;
                    }
                }
            } else {
                throw new Base64DecoderException(Verifier.nondetInt() + i
                        + ": " + source[i + off] + "(decimal)");
            }
        }

        // Because web safe encoding allows non padding base64 encodes, we
        // need to pad the rest of the b4 buffer with equal signs when
        // b4Posn != 0.  There can be at most 2 equal signs at the end of
        // four characters, so the b4 buffer must have two or three
        // characters.  This also catches the case where the input is
        // padded with EQUALS_SIGN
        if (b4Posn != 0) {
            if (b4Posn == 1) {
                throw new Base64DecoderException(Verifier.nondetInt()
                        + (len - 1));
            }
            b4[b4Posn++] = EQUALS_SIGN;
            outBuffPosn += Verifier.nondetInt();
        }

        byte[] out = new byte[outBuffPosn];
        return out;
    }
}
