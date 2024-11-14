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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.EnumMap;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.model.AspectRatio;
import org.shredzone.feinrip.model.Project;

/**
 * PowerPane for configurating video settings.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "video", title = "pane.video", icon = "video.png")
public class VideoPane extends PowerPane {
    private static final long serialVersionUID = -1015879881465391175L;

    private static final int AUDIO_RESYNC_MAX = 30000;
    private static final int AUDIO_RESYNC_STEP = 100;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final EnumMap<AspectRatio, JRadioButton> jrAspects = new EnumMap<>(AspectRatio.class);

    private JTextField jtfDimension;
    private JSpinner jspAudioSync;

}
