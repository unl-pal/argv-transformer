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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.shredzone.feinrip.model.Language;
import org.shredzone.feinrip.util.Command;


/**
 * Utility class for available languages.
 * <p>
 * Requires: <code>mkvtoolnix</code> package
 *
 * @author Richard "Shred" Körber
 */
public class LanguageUtils {
    private static final File MKVMERGE = new File("/usr/bin/mkvmerge");

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Pattern LANGUAGE_PATTERN = Pattern.compile("(.*?)(?:\\;[^|]+)?\\s*\\|\\s+([a-z]+)\\s+\\|\\s+([a-z]+)\\s+\\|(?:\\s+([a-z]+))?.*");

    private static Map<String, Language> langMap;
    private static List<Language> langList;

}
