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
package org.shredzone.feinrip.lsdvd;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import org.shredzone.feinrip.lsdvd.DvdAudioAttributes.Mode;
import org.shredzone.feinrip.lsdvd.DvdAudioAttributes.Type;
import org.shredzone.feinrip.lsdvd.DvdSubtitleAttributes.SubType;
import org.shredzone.feinrip.lsdvd.DvdTitleSet.Aspect;
import org.shredzone.feinrip.lsdvd.DvdTitleSet.Format;

/**
 * Specialized reader methods for accessing IFO and BUP files.
 *
 * @author Richard "Shred" Körber
 */
public class IfoRandomAccessFile extends RandomAccessFile {

    private static final long DVD_BLOCK_LENGTH = 2048;

    /**
     * Reads audio attributes and returns a {@link DvdAudioAttributes}. The cursor is
     * expected at the start of the audio attribute structure, and is placed at the start
     * of the next audio attribute structure.
     *
     * @return {@link DvdAudioAttributes} containing decoded audio attributes
     */
    public DvdAudioAttributes readAudioAttributes() throws IOException {
        DvdAudioAttributes audio = new DvdAudioAttributes();

        int flag1 = readu8();
        switch (flag1 & 0xE0) {
            case 0x00: audio.setMode(Mode.AC3);   break;
            case 0x40: audio.setMode(Mode.MPEG1); break;
            case 0x60: audio.setMode(Mode.MPEG2); break;
            case 0x80: audio.setMode(Mode.LPCM);  break;
            case 0xA0: audio.setMode(Mode.SDDS);  break;
            case 0xC0: audio.setMode(Mode.DTS);   break;
        }

        int flag2 = readu8();
        audio.setChannels((flag2 & 0x07) + 1);

        if ((flag1 & 0x0C) != 0x00) {
            audio.setLang(readFixedString(2));
        } else {
            skip(2);
        }

        int ext = skip(1).readu8();
        switch (ext) {
            case 1: audio.setType(Type.NORMAL);            break;
            case 2: audio.setType(Type.VISUALLY_IMPAIRED); break;
            case 3: audio.setType(Type.DIRECTORS_COMMENT); break;
            case 4: audio.setType(Type.ALTERNATE);         break;
        }

        skip(2);

        return audio;
    }

    /**
     * Reads a title set and returns a {@link DvdTitleSet}.
     *
     * @return {@link DvdTitleSet}
     */
    public DvdTitleSet readTitleSet() throws IOException {
        DvdTitleSet titleSet = new DvdTitleSet();

        int flag1 = at(0x200).readu8();
        boolean pal = (flag1 & 0x30) != 0x00;
        titleSet.setFormat(pal ? Format.PAL : Format.NTSC);
        titleSet.setAspect((flag1 & 0x0C) == 0x0C ? Aspect.ASPECT_16_9 : Aspect.ASPECT_4_3);
        titleSet.setLetterboxEnabled((flag1 & 0x01) == 0); // != 0 means disallowed!
        titleSet.setPanScanEnabled((flag1 & 0x02) == 0); // != 0 means disallowed!

        int flag2 = readu8();
        switch (flag2 & 0x38) {
            case 0x00:
                titleSet.setWidth(720);
                titleSet.setHeight(pal ? 576 : 480);
                break;

            case 0x08:
                titleSet.setWidth(704);
                titleSet.setHeight(pal ? 576 : 480);
                break;

            case 0x10:
                titleSet.setWidth(352);
                titleSet.setHeight(pal ? 576 : 480);
                break;

            case 0x18:
                titleSet.setWidth(352);
                titleSet.setHeight(pal ? 288 : 240);
                break;
        }

        int audios = readu16();
        for (int ix = 0; ix < 8; ix++) {
            if (ix < audios) {
                titleSet.getAudioAttributes().add(readAudioAttributes());
            } else {
                skip(8);
            }
        }

        skip(16);

        int subs = readu16();
        for (int ix = 0; ix < 32; ix++) {
            if (ix < subs) {
                titleSet.getSubAttributes().add(readSubtitleAttributes());
            } else {
                skip(6);
            }
        }

        return titleSet;
    }

}
