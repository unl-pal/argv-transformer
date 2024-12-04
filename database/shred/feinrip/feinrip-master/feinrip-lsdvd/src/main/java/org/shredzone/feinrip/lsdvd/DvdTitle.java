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
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Information about a single DVD title.
 *
 * @author Richard "Shred" Körber
 */
public class DvdTitle {

    private final int[] colors = new int[16];
    private final List<Long> chapterTimeMs = new ArrayList<>();
    private final List<DvdAudio> audios = new ArrayList<>();
    private final List<DvdSubtitle> subs = new ArrayList<>();
    private int title;
    private int chapters;
    private int angles;
    private long totalTimeMs;
    private DvdTitleSet titleSet;
    private int vtsn;
    private int vts;

}
