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
package org.shredzone.feinrip.model;

import java.util.prefs.Preferences;

/**
 * Configuration model. Changes are automatically persisted. This is a singleton.
 *
 * @author Richard "Shred" Körber
 */
public class Configuration {
    private static final Configuration INSTANCE = new Configuration();
    private static final String SOUNDFILE_KEY = "soundFile";
    private static final String FORCEAUDIODEMUX_KEY = "forceAudioDemux";
    private static final String PREPROCESS_KEY = "muxerPreprocess";
    private static final String PREPROCESS_SCRIPT_KEY = "muxerPreprocessScript";
    private static final String DVD_STREAMTYPE_KEY = "dvdStreamType";
    private static final String TEMP_DIR = "tempDir";
    private static final String IMDB_URL = "imdbUrl";
    private static final String IMDB_ENABLE = "imdbEnable";
    private static final String OMDB_ENABLE = "omdbEnable";
    private static final String OFDB_ENABLE = "ofdbEnable";
    private static final String TVDB_AIRED = "tvdbAired";

    private final Preferences prefs = Preferences.userNodeForPackage(Configuration.class);

}
