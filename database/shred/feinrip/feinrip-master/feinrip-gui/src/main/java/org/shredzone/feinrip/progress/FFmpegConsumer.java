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
package org.shredzone.feinrip.progress;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link LogConsumer} that is able to handle <code>ffmpeg</code> output.
 *
 * @author Richard "Shred" Körber
 */
public class FFmpegConsumer extends LogConsumer {

    private static final Pattern PERCENT = Pattern.compile(".*?size=.*?time=(\\d+)\\.(\\d\\d).*");
    private static final Pattern PERCENT2 = Pattern.compile(".*?size=.*?time=(\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d\\d).*");

    private long duration = 0;

}
