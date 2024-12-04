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

/**
 * Model for a single audio stream.
 *
 * @author Richard "Shred" Körber
 */
public class Audio {

    private int ix;
    private Language language;
    private String format;
    private AudioType type;
    private int channels;
    private int streamId;
    private boolean enabled;
    private boolean available = true;

}
