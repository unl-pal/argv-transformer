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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.shredzone.feinrip.gui.JToolbarButton;
import org.shredzone.feinrip.gui.NoListSelectionModel;
import org.shredzone.feinrip.gui.action.OpenChaptersAction;
import org.shredzone.feinrip.model.Chapter;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.model.Track;
import org.shredzone.feinrip.source.DvdSource;
import org.shredzone.feinrip.source.Source;

/**
 * PowerPane for configurating chapter settings.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "chapter", title = "pane.chapter", icon = "chapter.png")
public class ChapterPane extends PowerPane {
    private static final long serialVersionUID = -9076194361885838577L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private JScrollPane jScroll;
    private JPanel jpChapters;
    private JCheckBox jcbIgnore;
    private HashMap<JTextField, ChapterEditListener> listenerMap = new HashMap<>();
    private boolean noChapterUpdates;

    private class ChapterEditListener implements DocumentListener, FocusListener {
        private final Project project;
        private final JComponent parent;
        private final Chapter chapter;
    }

}
