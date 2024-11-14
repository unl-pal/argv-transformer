/*
 * feinrip
 *
 * Copyright (C) 2016 Richard "Shred" Körber
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
package org.shredzone.feinrip.util;

/**
 * A builder for log outputs. It also compresses duplicate lines, to reduce output.
 * <p>
 * The builder is line oriented. Each append is a single line. There is no need to add
 * line feed characters.
 *
 * @author Richard "Shred" Körber
 */
public class LogBuilder implements Appendable {

    private final StringBuilder sb = new StringBuilder();
    private String lastLine;
    private int lastLineCounter;

}
