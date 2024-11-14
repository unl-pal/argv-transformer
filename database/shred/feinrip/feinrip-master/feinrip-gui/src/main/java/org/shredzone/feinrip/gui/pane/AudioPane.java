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
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.shredzone.feinrip.gui.JLanguageEditableTable;
import org.shredzone.feinrip.gui.JToolbarButton;
import org.shredzone.feinrip.gui.action.TableSelectAllAction;
import org.shredzone.feinrip.gui.model.AudioTableModel;
import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.source.Source;

/**
 * PowerPane for configurating audio settings.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "audio", title = "pane.audio", icon = "audio.png")
public class AudioPane extends PowerPane {
    private static final long serialVersionUID = -390298616306980792L;

    private static final int MAX_AUDIO_BODY = 9;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private JLanguageEditableTable jtAudio;
    private TableSelectAllAction actionSelectAll;
    private TableSelectAllAction actionUnselectAll;

}
