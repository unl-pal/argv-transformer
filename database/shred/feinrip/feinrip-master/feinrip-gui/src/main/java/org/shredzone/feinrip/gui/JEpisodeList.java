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

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.shredzone.feinrip.database.TvdbService.TvdbEpisode;

/**
 * A {@link JList} of {@link TvdbEpisode}.
 *
 * @author Richard "Shred" Körber
 */
public class JEpisodeList extends JList<TvdbEpisode> {
    private static final long serialVersionUID = -1062043756891198887L;

    @SuppressWarnings({ "unchecked", "rawtypes" })  // Swing with Generics can be a real pita (tm)...
    private static class TvdbEpisodeListCellRenderer implements ListCellRenderer {
        private final ListCellRenderer delegate;
    }

}
