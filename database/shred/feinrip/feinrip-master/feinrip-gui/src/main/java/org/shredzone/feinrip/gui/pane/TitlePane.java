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
package org.shredzone.feinrip.gui.pane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.shredzone.feinrip.database.TvdbService.TvdbEpisode;
import org.shredzone.feinrip.gui.BorderAndFlowPanel;
import org.shredzone.feinrip.gui.ConfigurablePane;
import org.shredzone.feinrip.gui.DocumentListenerAdapter;
import org.shredzone.feinrip.gui.JEpisodeList;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.action.ImdbSyncAction;
import org.shredzone.feinrip.gui.action.NextEpisodeAction;
import org.shredzone.feinrip.gui.action.ProposeTitleAction;
import org.shredzone.feinrip.gui.action.TitleQueryAction;
import org.shredzone.feinrip.gui.action.TvdbQueryAction;
import org.shredzone.feinrip.gui.model.EpisodeListModel;
import org.shredzone.feinrip.model.Configuration;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.source.Source;

/**
 * PowerPane for configurating title settings.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "title", title = "pane.title", icon = "title.png")
public class TitlePane extends PowerPane implements ConfigurablePane, ListSelectionListener {
    private static final long serialVersionUID = 5465622345023874309L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final Configuration config = Configuration.global();

    private JTextField jtfTitle;
    private JEpisodeList jlEpisodes;
    private JTextField jtfImdbUrl;
    private JCheckBox jcbImdbEnabled;
    private JCheckBox jcbOfdbEnabled;
    private JCheckBox jcbOmdbEnabled;
    private JCheckBox jcbTvdbAired;

}
