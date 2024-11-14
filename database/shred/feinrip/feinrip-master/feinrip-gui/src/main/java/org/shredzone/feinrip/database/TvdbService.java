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
package org.shredzone.feinrip.database;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.shredzone.feinrip.model.Configuration;

/**
 * Service for accessing The Tvdb.
 *
 * @see <a href="http://thetvdb.com/">The TVDb</a>
 * @author Richard "Shred" Körber
 */
public class TvdbService {

    private static final String SITE = "https://api.thetvdb.com";
    private static final Pattern NAME_AND_YEAR_PATTERN = Pattern.compile("(.*?)\\s+\\(\\d+\\)");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+).*");
    private static final int TIMEOUT = 10000;
    private static final String ENCODING = "UTF-8";
    private static final Locale FALLBACK_LOCALE = Locale.ENGLISH;

    // It is permitted to publish the API key in the source code, see here:
    //   http://forums.thetvdb.com/viewtopic.php?f=17&t=8227
    // Please do not use this key for your own project, but request your own key.
    // It's easy and it's free: http://thetvdb.com/?tab=apiregister
    private static final String API_KEY = "A0FD47D057D7D4E2";

    /**
     * Represents a Tvdb series.
     *
     * @author Richard "Shred" Körber
     */
    public static class TvdbSeries {
        private long id;
        private String title;
        private String language;
        private String aired;
    }

    /**
     * Represents a Tvdb episode.
     *
     * @author Richard "Shred" Körber
     */
    public static class TvdbEpisode {
        private long id;
        private int season;
        private int episode;
        private String title;
    }

    private final static TvdbService instance = new TvdbService();

    private Set<String> availableLanguages;
    private String token;
}
