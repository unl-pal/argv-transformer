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
package org.shredzone.feinrip.gui.model;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.shredzone.feinrip.model.Language;
import org.shredzone.feinrip.model.Project;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.model.SubtitleFormat;
import org.shredzone.feinrip.model.SubtitleType;

/**
 * An editable {@link TableModel} for {@link Subtitle} entities.
 *
 * @author Richard "Shred" Körber
 */
public class SubTableModel implements SelectionTableModel {

    private static final ResourceBundle B = ResourceBundle.getBundle("message");

    private final Project project;
    private final Set<TableModelListener> listener = new HashSet<>();



}