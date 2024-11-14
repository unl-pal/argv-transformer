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
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.shredzone.feinrip.gui.BorderAndFlowPanel;
import org.shredzone.feinrip.gui.ConfigurablePane;
import org.shredzone.feinrip.gui.JLabelGroup;
import org.shredzone.feinrip.gui.SimpleFileFilter;
import org.shredzone.feinrip.gui.action.AbstractSyncAction;
import org.shredzone.feinrip.gui.source.SourceVobPane;
import org.shredzone.feinrip.model.Configuration;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.util.LogBuilder;

/**
 * PowerPane that shows the progress of a running conversion.
 *
 * @author Richard "Shred" Körber
 */
@Pane(name = "progress")
public class ProgressPane extends PowerPane implements ConfigurablePane, ProgressMeter {
    private static final long serialVersionUID = -4337805119426983223L;

    // Minimum delay between two percent meter updates, in milliseconds
    private static final long NEXT_PERCENT_LIMITER = 500;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon selectFileIcon = new ImageIcon(SourceVobPane.class.getResource("/org/shredzone/feinrip/icon/file.png"));

    private final Configuration config = Configuration.global();
    private final LogBuilder logBuilder = new LogBuilder();

    private JProgressBar jpbProgress;
    private JTextField jtfInfo;
    private JTextArea jtaLog;
    private JTextField jtfMp3File;
    private JTextField jtfTempDir;
    private JTextField jtfPreprocessorFile;
    private JCheckBox jcAudioDemux;
    private JCheckBox jcPreprocess;
    private Long startTime = null;
    private Frame frame;
    private String frameTitle;
    private long nextPercentOutput = 0;

    /**
     * Estimates the time until 100% will be reached.
     * <p>
     * A first estimation is available after 3 seconds.
     *
     * @param percent
     *            current percent
     * @return Estimated number of seconds until 100% will be reached. Returns -1 if an
     *         estimation is not currently available.
     */
    private int estimateTime(float percent) {
        if (startTime == null || percent <= 0 || percent > 100) return -1;

        long current = System.currentTimeMillis();
        long elapsed = current - startTime;

        // Wait at least 3 seconds before estimating.
        if (elapsed < 3000) return -1;

        // --- Compute ETA and required time ---
        // The ETA is extrapolated from the elapsed time and the index
        // in relation to the given maximum.
        double eta = ((elapsed * 100.0d) / percent) + startTime;
        double required = Math.floor((eta - current) / 1000.0d); // floored, in seconds

        // --- Out of range? ---
        if (required < 0 || required > Integer.MAX_VALUE) return -1;

        // --- Return the time ---
        return (int) required;
    }

    /**
     * Action for selecting a temp dir.
     */
    private class TempSelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = -5613199421054499694L;
    }

    /**
     * Action for selecting an mp3 file.
     */
    private class Mp3SelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = -4533364218037118291L;
    }

    /**
     * Action for selecting a script file.
     */
    private class ScriptSelectAction extends AbstractSyncAction {
        private static final long serialVersionUID = -1136470081289808134L;
    }

}
