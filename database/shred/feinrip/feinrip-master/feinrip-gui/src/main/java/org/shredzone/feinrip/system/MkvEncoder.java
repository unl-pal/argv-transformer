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

import static java.util.stream.Collectors.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.progress.LogConsumer;
import org.shredzone.feinrip.progress.PercentConsumer;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.util.Command;

/**
 * Service class for generating mkv files.
 * <p>
 * Main purpose is to assemble command line arguments and pass them to
 * <code>mkvmerge</code>.
 * <p>
 * However, <code>mkvmerge</code> does not always detect all audio streams in a source
 * vob file. For this reason, <code>mkvmerge</code> first analyzes the vob stream, putting
 * out all audio streams it found. This set of audio streams is aligned with the audio
 * streams found in the DVD structure. If there are streams missing, they will be
 * extracted in an intermediate step, and fed to <code>mkvmerge</code> separately.
 * <p>
 * Requires: <code>mkvtoolnix</code> package
 *
 * @author Richard "Shred" Körber
 */
public class MkvEncoder {
    private static final File MKVMERGE  = new File("/usr/bin/mkvmerge");

    private final Map<Audio, ExtAudio> audioMap = new HashMap<>();

    private Project project;
    private File vobFile;
    private File chapFile;
    private File eitFile;
    private Map<Integer, File> vobsubFiles;
    private boolean forceAudioDemux;

    private static class ExtAudio {
        Integer mkvStreamId;
        File file;
    }

}
