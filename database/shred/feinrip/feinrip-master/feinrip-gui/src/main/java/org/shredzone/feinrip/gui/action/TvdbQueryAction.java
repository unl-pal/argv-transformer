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
package org.shredzone.feinrip.gui.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.shredzone.feinrip.database.TvdbService;
import org.shredzone.feinrip.database.TvdbService.TvdbEpisode;
import org.shredzone.feinrip.database.TvdbService.TvdbSeries;
import org.shredzone.feinrip.gui.ErrorDialog;
import org.shredzone.feinrip.gui.model.SimpleArrayListModel;
import org.shredzone.feinrip.model.Project;

/**
 * Action that queries the TVDB by the title name and offers a selection of matching TV
 * series to the user. If the user picks a series, the episodes are downloaded and set up
 * in the Project model.
 *
 * @author Richard "Shred" Körber
 */
public class TvdbQueryAction extends AbstractAsyncAction implements PropertyChangeListener, ListSelectionListener {
    private static final long serialVersionUID = -5601680508716350160L;

    private static final int POPUP_WIDTH = 600;
    private static final int POPUP_HEIGHT = 300;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon tvdbIcon = new ImageIcon(TvdbQueryAction.class.getResource("/org/shredzone/feinrip/icon/find.png"));

    private final Project project;

    private List<TvdbSeries> series;
    private JPopupMenu popup;
    private JList<TvdbSeries> pickList;

}
