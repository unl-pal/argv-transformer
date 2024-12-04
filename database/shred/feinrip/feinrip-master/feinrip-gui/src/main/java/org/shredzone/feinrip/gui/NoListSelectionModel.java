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

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;

/**
 * This {@link DefaultListSelectionModel} also allows that no item can be selected at all.
 * Use this selection model if you want to disable a {@link JList}, but keep the content
 * readable. Besides allowing a fourth selection mode {@code NO_SELECTION}, it completely
 * behaves like the {@link DefaultListSelectionModel}.
 *
 * @author Richard "Shred" Körber
 */
public class NoListSelectionModel extends DefaultListSelectionModel {
    private static final long serialVersionUID = 393116339432230821L;

    public static final int NO_SELECTION = 9181;

    private boolean noselect = false;

}
