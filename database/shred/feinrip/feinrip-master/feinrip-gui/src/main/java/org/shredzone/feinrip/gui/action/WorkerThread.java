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

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

/**
 * Super class for Swing worker threads.
 * <p>
 * The operation is executed in a separate thread. If used in modal mode, the root pane is
 * blocked (via a glass pane) and a waiting cursor is being shown while processing.
 *
 * @author Richard "Shred" Körber
 */
public abstract class WorkerThread extends Thread {
    private final RootPaneContainer root;

    private boolean executing = false;

}
