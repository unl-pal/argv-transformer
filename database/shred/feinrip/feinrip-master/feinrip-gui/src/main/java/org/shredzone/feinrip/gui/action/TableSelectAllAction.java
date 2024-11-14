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

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.shredzone.feinrip.gui.model.SelectionTableModel;

/**
 * An action that selects (or unselects) all entries of a {@link SelectionTableModel}.
 *
 * @author Richard "Shred" Körber
 */
public class TableSelectAllAction extends AbstractSyncAction implements TableModelListener {
    private static final long serialVersionUID = 7608210988679697836L;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon selectAllIcon = new ImageIcon(TableSelectAllAction.class.getResource("/org/shredzone/feinrip/icon/select-all.png"));
    private static final Icon unselectAllIcon = new ImageIcon(TableSelectAllAction.class.getResource("/org/shredzone/feinrip/icon/unselect-all.png"));

    private final boolean select;

    private SelectionTableModel model;

}
