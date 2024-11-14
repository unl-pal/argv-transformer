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
package org.shredzone.feinrip;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.shredzone.feinrip.database.ImdbDatabase;
import org.shredzone.feinrip.gui.FeinripPane;

/**
 * Main class that starts <i>feinrip</i>.
 *
 * @author Richard "Shred" Körber
 */
public class Feinrip extends JFrame {
    private static final long serialVersionUID = -6575536880238285877L;
    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final Preferences prefs = Preferences.userNodeForPackage(Feinrip.class);

}
