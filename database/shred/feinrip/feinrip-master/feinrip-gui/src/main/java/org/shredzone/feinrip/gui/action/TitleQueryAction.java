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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.shredzone.feinrip.database.ImdbService;
import org.shredzone.feinrip.database.OfdbService;
import org.shredzone.feinrip.database.OmdbService;
import org.shredzone.feinrip.gui.ErrorDialog;
import org.shredzone.feinrip.gui.model.SimpleArrayListModel;
import org.shredzone.feinrip.model.Configuration;
import org.shredzone.feinrip.model.Project;

/**
 * Action that queries movie databases with the current title. A choice of the resulting
 * movie titles (along with the production year) is shown to the user.
 *
 * @author Richard "Shred" Körber
 */
public class TitleQueryAction extends AbstractAsyncAction implements PropertyChangeListener, ListSelectionListener {
    private static final long serialVersionUID = 2039193013819372281L;

    private static final int POPUP_WIDTH = 600;
    private static final int POPUP_HEIGHT = 300;

    private static final ResourceBundle B = ResourceBundle.getBundle("message");
    private static final Icon titleIcon = new ImageIcon(OpenChaptersAction.class.getResource("/org/shredzone/feinrip/icon/find.png"));

    private final Configuration config = Configuration.global();

    private final Project project;
    private List<String> options;
    private JPopupMenu popup;
    private JList<String> pickList;

}
