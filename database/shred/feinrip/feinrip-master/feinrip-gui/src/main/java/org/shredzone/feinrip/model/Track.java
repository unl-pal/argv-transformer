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
package org.shredzone.feinrip.model;

import java.awt.Dimension;

/**
 * A single track of a DVD style source.
 *
 * @author Richard "Shred" Körber
 */
public class Track {

    private int track;
    private String length;
    private int chapters;
    private AspectRatio aspect;
    private int angles;
    private Dimension dimension;

}
