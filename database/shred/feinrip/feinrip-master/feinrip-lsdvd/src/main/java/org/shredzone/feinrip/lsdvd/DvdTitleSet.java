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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link DvdTitleSet} contains information that is shared between one or more
 * {@link DvdTitle}. The information is found in a single VTS file.
 *
 * @author Richard "Shred" Körber
 */
public class DvdTitleSet {

    public enum Format { NTSC, PAL }
    public enum Aspect { ASPECT_4_3, ASPECT_16_9 }

    private final List<DvdAudioAttributes> audioAttrs = new ArrayList<>();
    private final List<DvdSubtitleAttributes> subAttrs = new ArrayList<>();
    private Format format;
    private Aspect aspect;
    private boolean panScanEnabled;
    private boolean letterboxEnabled;
    private int width;
    private int height;

}
