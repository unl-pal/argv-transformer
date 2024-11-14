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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.shredzone.feinrip.gui.BorderAndFlowPanel;
import org.shredzone.feinrip.gui.DocumentListenerAdapter;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.SimpleFileFilter;
import org.shredzone.feinrip.gui.action.AbstractSyncAction;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.model.TargetTemplate;

/**
 * PowerPane for configurating target file name settings.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "target", title = "pane.target", icon = "target.png")
public class TargetPane extends PowerPane {
    private static final long serialVersionUID = -7660584324877119554L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon selectIcon = new ImageIcon(TargetPane.class.getResource("/org/shredzone/feinrip/icon/file.png"));
    private static final Icon pickIcon = new ImageIcon(TargetPane.class.getResource("/org/shredzone/feinrip/icon/pick.png"));

    private static final String KEY = "lastPath";

    private final Preferences prefs = Preferences.userNodeForPackage(TargetPane.class);

    private JTextField jtfFile;
    private JButton jbSelect;

    /**
     * Action for setting a template path.
     */
    private class TemplateAction extends AbstractSyncAction {
        private static final long serialVersionUID = -4843148193926477594L;

        private final TargetTemplate template;
    }

    /**
     * Action for selecting a target file.
     */
    private class FileSelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = -6001332296352193981L;
    }

}
