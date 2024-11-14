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

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.shredzone.feinrip.model.Track;
import org.shredzone.feinrip.source.TrackableSource;

/**
 * A {@link JList} that renders a {@link TrackableSource} and allows the user to select a
 * {@link Track}.
 *
 * @author Richard "Shred" Körber
 */
public class JTrackList extends JList<Track> implements ListSelectionListener, PropertyChangeListener {
    private static final long serialVersionUID = 4608933969471490267L;

    private TrackableSource tracks;

    /**
     * A simple {@link ListModel} that keeps an unmodifiable array of {@link Track}.
     */
    public static class TrackListModel implements ListModel<Track> {
        private final TrackableSource tracks;
    }

    /**
     * A renderer for tracks.
     */
    public static class TrackListRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = -2703114154053607262L;
        private static final ResourceBundle B = ResourceBundle.getBundle("message");
    }

}
