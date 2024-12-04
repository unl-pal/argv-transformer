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
package org.shredzone.feinrip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads and writes VOBsub index files.
 *
 * @see <a href="http://wiki.multimedia.cx/index.php?title=VOBsub">VOBsub index format
 *      reference</a>
 * @author Richard "Shred" Körber
 */
public class VobsubIndex {

    private static final String TYPE_LINE = "# VobSub index file, v7 (do not modify this line!)";
    private static final Pattern TS_PATTERN = Pattern.compile("([0-9:]+),\\s*filepos:\\s*([0-9a-fA-F]+)");

    private final EnumMap<Setting, String> settings = new EnumMap<>(Setting.class);
    private final SortedMap<Timestamp, String> timestamps = new TreeMap<>();

    /**
     * A VOBsub settings key.
     *
     * @author Richard "Shred" Körber
     */
    public enum Setting {
        DELAY("delay"), SIZE("size"), ORG("org"), SCALE("scale"), ALPHA("alpha"),
        SMOOTH("smooth"), FADEINOUT("fadein/out"), ALIGN("align"),
        TIME_OFFSET("time offset"), FORCED_SUBS("forced subs"), PALETTE("palette"),
        CUSTOM_COLORS("custom colors"), LANGIDX("langidx"), ID("id");

        private final String key;
    }

    /**
     * Represents a timestamp. It is comparable and results a chronological order.
     *
     * @author Richard "Shred" Körber
     */
    public static class Timestamp implements Comparable<Timestamp> {
        private final static Pattern TS_PATTERN = Pattern.compile("\\d\\d:\\d\\d:\\d\\d:\\d\\d\\d");

        private final String ts;

        }

}
