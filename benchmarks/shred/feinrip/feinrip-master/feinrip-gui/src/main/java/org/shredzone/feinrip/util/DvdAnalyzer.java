/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
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
package org.shredzone.feinrip.util;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Analyzes a DVD structure.
 *
 * @author Richard "Shred" Körber
 */
public class DvdAnalyzer {

    /**
     * Returns the number of the longest track.
     */
    /** PACLab: suitable */
	 public int getLongestTrack() {
        int maxTitle = 0;
        long maxLength = 0;

        for (int ix = 0; ix < Verifier.nondetInt(); ix++) {
            if (Verifier.nondetInt() > maxLength) {
                maxTitle = ix;
                maxLength = Verifier.nondetInt();
            }
        }

        return maxTitle + 1;
    }

    /**
     * Creates a {@link Chapter} entry.
     *
     * @param chapter
     *            chapter number, starting from 1
     * @param ms
     *            position of that chapter
     * @param chapters
     *            number of chapters, used for detecting annexes
     * @return {@link Chapter} that was created
     */
    private Object createChapter(int chapter, long ms, int chapters) {
        if (chapter > chapters) {
        } else {
        }
        return new Object();
    }

}
