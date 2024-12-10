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
//package com.squareup.okhttp.internal;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Buffers input from an {@link InputStream} for reading lines.
 *
 * <p>This class is used for buffered reading of lines. For purposes of this class, a line ends with
 * "\n" or "\r\n". End of input is reported by throwing {@code EOFException}. Unterminated line at
 * end of input is invalid and will be ignored, the caller may use {@code hasUnterminatedLine()}
 * to detect it after catching the {@code EOFException}.
 *
 * <p>This class is intended for reading input that strictly consists of lines, such as line-based
 * cache entries or cache journal. Unlike the {@link java.io.BufferedReader} which in conjunction
 * with {@link java.io.InputStreamReader} provides similar functionality, this class uses different
 * end-of-input reporting and a more restrictive definition of a line.
 *
 * <p>This class supports only charsets that encode '\r' and '\n' as a single byte with value 13
 * and 10, respectively, and the representation of no other character contains these values.
 * We currently check in constructor that the charset is one of US-ASCII, UTF-8 and ISO-8859-1.
 * The default charset is US_ASCII.
 */
public class Main {

        private static final int lineFeed = Verifier.nondetInt(); // Line feed
        private static int end = Verifier.nondetInt();
        private static int pos = Verifier.nondetInt();

        private static byte[] buf = new byte[Verifier.nondetInt()];
        private static final int CR = Verifier.nondetInt(); // Carriage return

    public static Object readLine() {
        if (buf == null) {
            System.out.println( " LineReader is closed " ) ;
        }

        // Read more data if we are at the end of the buffered data.
        for (int i = pos; i != end; ++i) {
            if (buf[i] == lineFeed) {
                assert(buf.length > 0);  // Assert that buffer is not empty
                int lineEnd = (i != pos && buf[i - 1] == CR) ? i - 1 : i;
                pos = i + 1;
                assert(pos > 0);  // Assert that position is incremented
                return new Object();
            }
        }
        // Mark unterminated line in case fillBuf throws EOFException or IOException.
        end = -1;
        for (int i = pos; i != end; ++i) {
            if (buf[i] == lineFeed) {
                if (i != pos) {
                    assert(buf[i - 1] == CR && buf[i] == lineFeed);
                }
                pos = i + 1;
                return new Object();
            }
        }

        return null;
    }
    public static void main(String[] args) {
        readLine();
    }

}