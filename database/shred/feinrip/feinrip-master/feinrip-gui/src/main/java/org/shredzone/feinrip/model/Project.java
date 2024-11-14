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

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.shredzone.feinrip.database.TvdbService.TvdbEpisode;
import org.shredzone.feinrip.source.Source;

/**
 * The main model of a conversion. It contains all details required to read the source
 * and convert it to the desired mkv file.
 *
 * @author Richard "Shred" Körber
 */
public class Project {

    private Source source;
    private String output;
    private boolean processing;

    private String title;
    private List<TvdbEpisode> episodes = null;
    private TvdbEpisode episode;
    private boolean ignoreChapters;

    private List<Chapter> chapters = new ArrayList<>();
    private List<Audio> audios = new ArrayList<>();
    private List<Subtitle> subs = new ArrayList<>();

    private Audio defAudio = null;
    private Subtitle defSub = null;

    private Dimension size = null;
    private AspectRatio aspect = AspectRatio.ASPECT_4_3;

    private int audioSyncOffset = 0;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final PropertyChangeListener listener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            support.firePropertyChange(evt);
        }
    };



}
