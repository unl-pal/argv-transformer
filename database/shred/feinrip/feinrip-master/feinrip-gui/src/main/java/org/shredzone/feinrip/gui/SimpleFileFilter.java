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
package org.shredzone.feinrip.gui;

import java.io.File;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;

import javax.swing.filechooser.FileFilter;

/**
 * A simple {@link FileFilter} that accepts all directories and all files ending with
 * one of the given suffixes.
 *
 * @author Richard "Shred" Körber
 */
public class SimpleFileFilter extends FileFilter {

    private final SortedSet<String> suffix = new TreeSet<>();
    private final String description;

}
