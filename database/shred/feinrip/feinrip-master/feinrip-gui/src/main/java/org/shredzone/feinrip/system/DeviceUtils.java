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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.shredzone.feinrip.model.MountPoint;
import org.shredzone.feinrip.util.Command;


/**
 * Utility class for finding DVD devices and their mount points.
 * <p>
 * Only works on Linuxoids.
 *
 * @author Richard "Shred" Körber
 */
public class DeviceUtils {
    private static final File MOUNT = new File("/usr/bin/mount");
    private static final File EJECT = new File("/usr/bin/eject");

    private static final long POLL_FREQUENCY = 1500L;

    private static Set<MediaChangeListener> listener = new HashSet<>();
    private static Thread changeThread;

}
