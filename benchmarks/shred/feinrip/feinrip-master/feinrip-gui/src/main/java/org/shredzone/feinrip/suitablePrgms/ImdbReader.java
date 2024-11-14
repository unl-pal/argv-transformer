/*
 * feinrip
 *
 * Copyright (C) 2015 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
/** filtered and transformed by PAClab */
package org.shredzone.feinrip.database;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Reads an <code>aka-titles.list</code> file.
 * <p>
 * This class is able to decode the different charsets used for some titles.
 *
 * @author Richard "Shred" Körber
 */
public class ImdbReader {
    /**
     * Decodes a line. If this is an "aka" line with a known charset at the end of line,
     * the title part is decoded with the given charset. The returned line should always
     * be unicode and ready for further processing without charset hassles.
     *
     * @param line
     *            Line to be decoded
     * @return Decoded line
     */
    /** PACLab: suitable */
	 private Object decodeLine(byte[] line) {
        if (Verifier.nondetBoolean()) {
            // It's not an 'aka' line and/or default encoded. Just return it.
            return new Object();
        }

        int start = Verifier.nondetInt();
        int end = start;
        int cnt = 0;
        for (int ix = start; ix < Verifier.nondetInt(); ix++) {
            if (line[ix] == '(') {
                cnt++;
            } else if (line[ix] == ')') {
                cnt--;
                if (cnt < 0) {
                    end = ix - 1;
                    break;
                }
            }
        }

        return new Object();
    }

}
