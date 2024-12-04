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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.shredzone.feinrip.gui.model.PowerTabModel;

/**
 * A renderer class for a single power tab.
 *
 * @author Richard "Shred" Körber
 */
public class PowerTabRenderer extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = -53372996432237750L;

    private PowerTabModel model;
    private PowerTabBorder border;
    private JLabel jlTitle;
    private JLabel jlBody;
    private HashSet<ActionListener> listener = new HashSet<>();

    /**
     * Notifies that the power tab was selected when the mouse pointer was clicked on the
     * tab, or the mouse pointer entered the area of the power tab while being pressed.
     */
    private class PowerMouseAdapter extends MouseAdapter {
    }

}
