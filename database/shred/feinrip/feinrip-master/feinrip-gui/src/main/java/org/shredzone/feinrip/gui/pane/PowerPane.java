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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.shredzone.feinrip.gui.model.PowerTabModel;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.source.Source;

/**
 * Abstract superclass for all PowerPanes.
 *
 * @author Richard "Shred" Körber
 */
public abstract class PowerPane extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = 9124999414210494139L;
    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final PowerTabModel powerTabModel = new PowerTabModel();
    protected final Project project;

}
