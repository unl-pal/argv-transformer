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
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.shredzone.feinrip.gui.BorderAndFlowPanel;
import org.shredzone.feinrip.gui.ConfigurablePane;
import org.shredzone.feinrip.gui.ErrorDialog;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.JToolbarButton;
import org.shredzone.feinrip.gui.JTrackList;
import org.shredzone.feinrip.gui.SimpleFileFilter;
import org.shredzone.feinrip.gui.action.AbstractAsyncAction;
import org.shredzone.feinrip.gui.action.AbstractSyncAction;
import org.shredzone.feinrip.model.MountPoint;
import org.shredzone.feinrip.model.StreamType;
import org.shredzone.feinrip.source.DvdSource;
import org.shredzone.feinrip.source.Source;
import org.shredzone.feinrip.system.DeviceUtils;
import org.shredzone.feinrip.system.MediaChangeListener;

/**
 * A source configuration panel for DVD sources.
 *
 * @author Richard "Shred" Körber
 */
public class SourceDvdPane extends SourceSelectorPane implements ConfigurablePane,
            ListSelectionListener, MediaChangeListener, PropertyChangeListener {
    private static final long serialVersionUID = 8155941897642114923L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon ejectIcon = new ImageIcon(SourceDvdPane.class.getResource("/org/shredzone/feinrip/icon/eject.png"));
    private static final Icon selectIcon = new ImageIcon(SourceDvdPane.class.getResource("/org/shredzone/feinrip/icon/large-dvd.png"));
    private static final Icon reloadIcon = new ImageIcon(SourceDvdPane.class.getResource("/org/shredzone/feinrip/icon/reload.png"));
    private static final Icon selectFileIcon = new ImageIcon(SourceVobPane.class.getResource("/org/shredzone/feinrip/icon/file.png"));
    private static final String KEY = "lastEitPath";

    private final Preferences prefs = Preferences.userNodeForPackage(SourceDvdPane.class);
    private final DvdSource source = new DvdSource();

    private JList<MountPoint> jlDevice;
    private JComboBox<StreamType> jcbRipMode;
    private JTrackList jlTrack;
    private JTextField jtfEitFile;

    /**
     * Action for reloading the list of available DVDs.
     */
    private class DvdReloadAction extends AbstractAsyncAction {
        private static final long serialVersionUID = -6392058002300620916L;
    }

    /**
     * Action for ejecting the currently selected DVD.
     */
    private class DvdEjectAction extends AbstractAsyncAction {
        private static final long serialVersionUID = 7474719755768224845L;
    }

    /**
     * Renderer for mount point lists.
     */
    private static class MointPointListCellRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = -6891744883662396982L;
    }

    /**
     * Action for selecting an eit file.
     */
    private class EitSelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = 581152181550511413L;
    }

}
