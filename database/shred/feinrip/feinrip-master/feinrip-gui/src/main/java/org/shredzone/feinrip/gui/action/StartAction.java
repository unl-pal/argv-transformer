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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.shredzone.feinrip.FeinripProcessor;
import org.shredzone.feinrip.gui.ErrorDialog;
import org.shredzone.feinrip.gui.FeinripPane;
import org.shredzone.feinrip.gui.pane.ProgressPane;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.source.Source;

/**
 * Action for processing the sources based on the Project, and generating the mkv file.
 *
 * @author Richard "Shred" Körber
 */
public class StartAction extends AbstractAsyncAction implements PropertyChangeListener {
    private static final long serialVersionUID = 7368475121002513675L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon playIcon = new ImageIcon(StartAction.class.getResource("/org/shredzone/feinrip/icon/play.png"));

    private final Project project;
    private final FeinripPane master;
    private final ProgressPane progress;

}
