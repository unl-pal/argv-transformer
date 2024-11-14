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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shredzone.feinrip.dvb.DvbInputStream;
import org.shredzone.feinrip.dvb.si.EventInformation;
import org.shredzone.feinrip.dvb.si.descriptor.Descriptor;
import org.shredzone.feinrip.dvb.si.descriptor.ShortEventDescriptor;
import org.shredzone.feinrip.model.Subtitle;

/**
 * Analyzes eit files and srt files of recorded TV.
 * <p>
 * This class heavily depends on some private scripts I wrote for recording TV shows
 * by my Dreambox and burning them on DVD. Basically the <code>movie.eit</code> file is
 * the eit file written by the Dreambox along with the recorded TV show. It contains the
 * movie title, a plot summary, and the recording date and time. The <code>.srt</code>
 * files are subtitle texts extracted from the TV teletext stream.
 *
 * @author Richard "Shred" Körber
 */
public class EitAnalyzer {

    private static final Pattern YEAR_PATTERN = Pattern.compile(".*?(\\d{4}).*");

}
