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
package org.shredzone.feinrip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.shredzone.feinrip.audio.PlaySoundFx;
import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.Configuration;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.source.Source;
import org.shredzone.feinrip.system.ChapterUtils;
import org.shredzone.feinrip.system.MkvEncoder;
import org.shredzone.feinrip.system.PreprocessorInvoker;
import org.shredzone.feinrip.system.StreamUtils;

/**
 * The main processor for generating mkv files.
 *
 * @author Richard "Shred" Körber
 */
public class FeinripProcessor {
    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final Configuration config = Configuration.global();
    private final Project project;
    private ProgressMeter progressMeter;
    private File chapFile;
    private File vobFile;
    private File eitFile;
    private Map<Integer, File> vobsubFiles = new HashMap<>();
    private List<File> audioFiles = new ArrayList<>();



}
