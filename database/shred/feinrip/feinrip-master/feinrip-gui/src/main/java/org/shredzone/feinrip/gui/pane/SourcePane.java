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
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import org.shredzone.feinrip.gui.ConfigurablePane;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.source.SourceDvdPane;
import org.shredzone.feinrip.gui.source.SourceIsoPane;
import org.shredzone.feinrip.gui.source.SourceVobPane;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.source.Source;

/**
 * PowerPane for configurating source settings.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "source", title = "pane.source", icon = "small-dvd.png")
public class SourcePane extends PowerPane implements ConfigurablePane {
    private static final long serialVersionUID = -3519322844850173205L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final ImageIcon dvdIcon = new ImageIcon(SourcePane.class.getResource("/org/shredzone/feinrip/icon/large-dvd.png"));
    private static final ImageIcon dvdIconSmall = new ImageIcon(SourcePane.class.getResource("/org/shredzone/feinrip/icon/small-dvd.png"));
    private static final ImageIcon isoIcon = new ImageIcon(SourcePane.class.getResource("/org/shredzone/feinrip/icon/large-iso.png"));
    private static final ImageIcon isoIconSmall = new ImageIcon(SourcePane.class.getResource("/org/shredzone/feinrip/icon/small-iso.png"));
    private static final ImageIcon vobIcon = new ImageIcon(SourcePane.class.getResource("/org/shredzone/feinrip/icon/large-vob.png"));
    private static final ImageIcon vobIconSmall = new ImageIcon(SourcePane.class.getResource("/org/shredzone/feinrip/icon/small-vob.png"));

    private ButtonGroup sourceGroup;
    private JToggleButton jtbDvdSource;
    private JToggleButton jtbIsoSource;
    private JToggleButton jtbVobSource;
    private CardLayout setupLayout;
    private JPanel jpSetup;

    private SourceDvdPane sourceDvdPane;
    private SourceIsoPane sourceIsoPane;
    private SourceVobPane sourceVobPane;

}
