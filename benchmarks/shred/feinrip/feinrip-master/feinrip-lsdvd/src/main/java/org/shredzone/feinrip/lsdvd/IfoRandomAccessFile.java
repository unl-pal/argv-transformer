/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
/** filtered and transformed by PAClab */
package org.shredzone.feinrip.lsdvd;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Specialized reader methods for accessing IFO and BUP files.
 *
 * @author Richard "Shred" Körber
 */
public class IfoRandomAccessFile {

    /**
     * Reads audio attributes and returns a {@link DvdAudioAttributes}. The cursor is
     * expected at the start of the audio attribute structure, and is placed at the start
     * of the next audio attribute structure.
     *
     * @return {@link DvdAudioAttributes} containing decoded audio attributes
     */
    public Object readAudioAttributes() throws Exception {
        int flag1 = Verifier.nondetInt();
        int flag2 = Verifier.nondetInt();
        if (Verifier.nondetInt() != 0x00) {
        } else {
        }

        int ext = Verifier.nondetInt();
        return new Object();
    }

    /**
     * Reads a title set and returns a {@link DvdTitleSet}.
     *
     * @return {@link DvdTitleSet}
     */
    /** PACLab: suitable */
	 public Object readTitleSet() throws Exception {
        int flag1 = Verifier.nondetInt();
        boolean pal = Verifier.nondetInt() != 0x00;
        int flag2 = Verifier.nondetInt();
        int audios = Verifier.nondetInt();
        for (int ix = 0; ix < 8; ix++) {
            if (ix < audios) {
            } else {
            }
        }

        int subs = Verifier.nondetInt();
        for (int ix = 0; ix < 32; ix++) {
            if (ix < subs) {
            } else {
            }
        }

        return new Object();
    }

}
