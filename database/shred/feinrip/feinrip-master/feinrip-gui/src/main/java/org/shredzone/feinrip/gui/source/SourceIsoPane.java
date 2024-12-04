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
package org.shredzone.feinrip.gui.source;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.shredzone.feinrip.gui.BorderAndFlowPanel;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.JTrackList;
import org.shredzone.feinrip.gui.action.AbstractSyncAction;
import org.shredzone.feinrip.model.MountPoint;
import org.shredzone.feinrip.source.IsoSource;
import org.shredzone.feinrip.source.Source;

/**
 * A source configuration panel for DVD iso directories.
 *
 * @author Richard "Shred" Körber
 */
public class SourceIsoPane extends SourceSelectorPane {
    private static final long serialVersionUID = 6853299422072887626L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon selectIcon = new ImageIcon(SourceIsoPane.class.getResource("/org/shredzone/feinrip/icon/file.png"));
    private static final String KEY = "lastIsoDir";

    private final Preferences prefs = Preferences.userNodeForPackage(SourceIsoPane.class);
    private final IsoSource source = new IsoSource();

    private JTextField jtfDir;
    private JTrackList jlTrack;

    /**
     * Action for selecting an iso directory.
     */
    private class DirSelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = -7741031192975942044L;
    }

}
