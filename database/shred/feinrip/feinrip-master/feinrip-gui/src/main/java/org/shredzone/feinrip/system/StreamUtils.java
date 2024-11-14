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
package org.shredzone.feinrip.system;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.StreamType;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.progress.FFmpegConsumer;
import org.shredzone.feinrip.progress.FilteringConsumer;
import org.shredzone.feinrip.progress.LogConsumer;
import org.shredzone.feinrip.progress.PercentConsumer;
import org.shredzone.feinrip.progress.PredicateLogConsumer;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.util.Command;

/**
 * Utility class for generic streaming operations.
 * <p>
 * Requires: <code>ffmpeg</code>, <code>mplayer</code>, <code>transcode</code>,
 * <code>mencoder</code> packages
 *
 * @author Richard "Shred" Körber
 */
public class StreamUtils {
    private static final File FFMPEG    = new File("/usr/bin/ffmpeg");
    private static final File MENCODER  = new File("/usr/bin/mencoder");
    private static final File MPLAYER   = new File("/usr/bin/mplayer");
    private static final File TCCAT     = new File("/usr/bin/tccat");

    private static final String PROBESIZE = "50M";

    /**
     * A helper class that collects the analyzed data.
     */
    private static class AnalyzerCollector implements Consumer<String> {
        private String ffStreamId;
        private String suffix;
        private String acodec = "copy";
        private Long duration = null;

        private static final Pattern DURATION = Pattern.compile(".*?Duration: (\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d\\d).*");
        private final Pattern streamPattern;
    }

}
