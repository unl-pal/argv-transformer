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
package org.shredzone.feinrip.source;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.shredzone.feinrip.database.TvdbService.TvdbEpisode;
import org.shredzone.feinrip.model.Project;

/**
 * Abstract implementation of {@link Source}.
 *
 * @author Richard "Shred" Körber
 */
public abstract class AbstractSource implements Source {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * {@link Project} the source is bound to.
     */
    protected Project project;

}
