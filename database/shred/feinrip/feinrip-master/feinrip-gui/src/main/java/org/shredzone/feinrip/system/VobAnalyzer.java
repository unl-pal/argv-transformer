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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shredzone.feinrip.model.AspectRatio;
import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.util.Command;

/**
 * Analyzes a vob file.
 * <p>
 * Requires: <code>ffmpeg</code> package
 *
 * @author Richard "Shred" Körber
 */
public class VobAnalyzer {
    private static final File FFMPEG = new File("/usr/bin/ffmpeg");

    private static final String PROBESIZE = "400M";
    private static final Pattern VIDEO_PATTERN = Pattern.compile(".*?Stream.*?Video.*?(\\d+)x(\\d+).*?DAR.(\\d+.\\d+).*");
    private static final Pattern AUDIO_PATTERN = Pattern.compile(".*?Stream.*?\\[0x([0-9a-fA-F]{2,4})\\].*?Audio.*?(mp2|mp3|ac3|dca|dts).*?(mono|stereo|\\d.channels|\\d\\.\\d).*");
    private static final Pattern SUB_PATTERN = Pattern.compile(".*?Stream.*?\\[(0x..)\\].*?Subtitle.*");

    private AspectRatio aspect;
    private Dimension dimension;
    private List<Audio> audios = new ArrayList<>();
    private List<Subtitle> subs = new ArrayList<>();



}
