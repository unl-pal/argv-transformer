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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.shredzone.feinrip.database.TvdbService.TvdbEpisode;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.source.Source;
import org.shredzone.feinrip.source.TrackableSource;

/**
 * Action that selects the next episode and next DVD track on TV series.
 * <p>
 * This action is only enabled if the source is offering tracks, episodes are available,
 * and the last track or episode has not been reached.
 *
 * @author Richard "Shred" Körber
 */
public class NextEpisodeAction extends AbstractSyncAction implements PropertyChangeListener {
    private static final long serialVersionUID = 1792629845377104609L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon nextIcon = new ImageIcon(NextEpisodeAction.class.getResource("/org/shredzone/feinrip/icon/next.png"));

    private final Project project;

}
