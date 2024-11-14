/*
 * feinrip
 *
 * Copyright (C) 2016 Richard "Shred" Körber
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

/**
 * Generic information about all audio streams in a title set.
 *
 * @author Richard "Shred" Körber
 */
public class DvdAudioAttributes {

    public enum Mode {
        MPEG1(0xC0), MPEG2(0xC0), AC3(0x80), LPCM(0xA0), DTS(0x88), SDDS(0x80);

        private final int baseStreamId;

        public enum Type { NORMAL, VISUALLY_IMPAIRED, DIRECTORS_COMMENT, ALTERNATE }

    private Mode mode;
    private int channels;
    private Type type;
    private String lang;

}
