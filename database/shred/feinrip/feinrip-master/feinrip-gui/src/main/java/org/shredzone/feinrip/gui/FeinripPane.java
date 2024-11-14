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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.shredzone.feinrip.gui.action.StartAction;
import org.shredzone.feinrip.gui.pane.AboutPane;
import org.shredzone.feinrip.gui.pane.AudioPane;
import org.shredzone.feinrip.gui.pane.ChapterPane;
import org.shredzone.feinrip.gui.pane.PowerPane;
import org.shredzone.feinrip.gui.pane.ProgressPane;
import org.shredzone.feinrip.gui.pane.SettingsPane;
import org.shredzone.feinrip.gui.pane.SourcePane;
import org.shredzone.feinrip.gui.pane.SubPane;
import org.shredzone.feinrip.gui.pane.TargetPane;
import org.shredzone.feinrip.gui.pane.TitlePane;
import org.shredzone.feinrip.gui.pane.VideoPane;
import org.shredzone.feinrip.model.Project;

/**
 * Main pane of feinrip. Each {@link FeinripPane} is bound to one {@link Project}.
 *
 * @author Richard "Shred" Körber
 */
public class FeinripPane extends JPanel {
    private static final long serialVersionUID = 504145006959520172L;

    private final Project project = new Project();

    private final AtomicReference<JLabelGroup> lgRef = new AtomicReference<>();

    private PowerTabPane jTabs;
    private String oldTab;
    private SettingsPane settingsPane;

}
