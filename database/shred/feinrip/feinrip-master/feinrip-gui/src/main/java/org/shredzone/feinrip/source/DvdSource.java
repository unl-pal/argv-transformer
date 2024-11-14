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
package org.shredzone.feinrip.source;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.Chapter;
import org.shredzone.feinrip.model.Configuration;
import org.shredzone.feinrip.model.MountPoint;
import org.shredzone.feinrip.model.StreamType;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.model.Track;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.system.EitAnalyzer;
import org.shredzone.feinrip.system.StreamUtils;
import org.shredzone.feinrip.util.DvdAnalyzer;
import org.shredzone.feinrip.util.VobsubIndex;
import org.shredzone.feinrip.util.VobsubIndex.Setting;

/**
 * A {@link Source} for physical DVDs.
 *
 * @author Richard "Shred" Körber
 */
public class DvdSource extends AbstractSource implements TrackableSource {

    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final Configuration config = Configuration.global();

    private File device;
    private File mountPoint;
    private File eitFile;
    private DvdAnalyzer dvd;
    private List<Track> tracks;
    private Track track;
    private boolean vobCorrupted = false;

}
