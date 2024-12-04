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

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

import org.shredzone.feinrip.model.Language;
import org.shredzone.feinrip.system.LanguageUtils;

/**
 * A {@link JTable} that is enabled to edit {@link Language} cells.
 *
 * @author Richard "Shred" Körber
 */
public class JLanguageEditableTable extends JTable {
    private static final long serialVersionUID = 3391769364717652460L;

    private static final int LANGUAGE_MIN_WIDTH = 180;

    /**
     * {@link ListCellRenderer} that shows a separator below the "undefined" language.
     */
    private static class LanguageListCellRenderer implements ListCellRenderer<Language> {
        private final Border separator = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY);
        private final ListCellRenderer<? super Language> delegate;
    }

}
