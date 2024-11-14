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
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.shredzone.feinrip.gui.model.PowerTabModel;

/**
 * PowerTabs are similar to standard tabbed panes. However, besides an icon and a title,
 * each tab may contain a body with multiple lines of information.
 * <p>
 * PowerTabs are always rendered at the right and with a fixed width.
 * <p>
 * There is a list area at the top of the PowerTabs, and an action area at the bottom.
 *
 * @author Richard "Shred" Körber
 */
public class PowerTabPane extends JPanel {
    private static final long serialVersionUID = -6086300562254539308L;

    private static final int MIN_WIDTH = 150;

    private CardLayout cardLayout;
    private JPanel centerPane;
    private JPanel listPane;
    private JPanel actionPane;
    private HashMap<String, PowerTabModel> modelMap = new HashMap<>();
    private HashMap<PowerTabRenderer, String> rendererMap = new HashMap<>();



}
