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
package org.shredzone.feinrip.gui.source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.shredzone.feinrip.gui.BorderAndFlowPanel;
import org.shredzone.feinrip.gui.ErrorDialog;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.SimpleFileFilter;
import org.shredzone.feinrip.gui.action.AbstractAsyncAction;
import org.shredzone.feinrip.gui.action.AbstractSyncAction;
import org.shredzone.feinrip.model.Palette;
import org.shredzone.feinrip.model.PaletteType;
import org.shredzone.feinrip.source.Source;
import org.shredzone.feinrip.source.VobSource;

/**
 * A source configuration pane for VOB files.
 *
 * @author Richard "Shred" Körber
 */
public class SourceVobPane extends SourceSelectorPane {
    private static final long serialVersionUID = 1542369139876207725L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon selectIcon = new ImageIcon(SourceVobPane.class.getResource("/org/shredzone/feinrip/icon/file.png"));
    private static final String KEY = "lastPath";

    private final Preferences prefs = Preferences.userNodeForPackage(SourceVobPane.class);
    private final VobSource source = new VobSource();

    private JTextField jtfFile;
    private JTextField jtfEitFile;
    private JComboBox<PaletteType> jcbPalette;
    private JTextField jtfCustomPalette;
    private JLabel[] jlColors = new JLabel[16];

    private void updateColors() {
        Palette colors;
        if (source.getPalette() == PaletteType.CUSTOM) {
            colors = source.getCustomPalette();
        } else {
            colors = source.getPalette().getPalette();
        }

        if (colors != null) {
            for (int ix = 0; ix < colors.size(); ix++) {
                jlColors[ix].setBackground(new Color(colors.getRgb(ix)));
                jlColors[ix].setForeground(colors.getBrightness(ix) < 100 ? Color.WHITE : Color.BLACK);
            }
        }
    }

    /**
     * Action to load a yuv palette file
     */
    private class LoadPaletteAction extends AbstractAsyncAction {
        private static final long serialVersionUID = 8386797533663223880L;

        private File selected;
    }

    /**
     * Action for selecting a vob file.
     */
    private class FileSelectAction extends AbstractAsyncAction {
        private static final long serialVersionUID = -2328099767483540149L;

        private File selected;
    }

    /**
     * Action for selecting an eit file.
     */
    private class EitSelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = -809157912320872123L;
    }

}
