/*
 * feinrip
 *
 * Copyright (C) 2015 Richard "Shred" Körber
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
package org.shredzone.feinrip.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads an <code>aka-titles.list</code> file.
 * <p>
 * This class is able to decode the different charsets used for some titles.
 *
 * @author Richard "Shred" Körber
 */
public class ImdbReader implements AutoCloseable {
    private static final char CR = '\n';
    private static final Charset DEFAULT_CHARSET = Charset.forName("iso-8859-1");

    private static final Pattern AKA_PATTERN = Pattern.compile(
                    "\\s+\\(aka.*?\\).*\\([^)]+(ISO-LATIN-\\d+|ISO-8859-\\d+|KOI8-R)[^)]+\\)",
                    Pattern.CASE_INSENSITIVE);

    private static final Pattern LATIN_PATTERN = Pattern.compile(
                    "ISO-LATIN-(\\d+)",
                    Pattern.CASE_INSENSITIVE);

    private final InputStream in;
    private final ByteArrayOutputStream lineBuffer = new ByteArrayOutputStream();

    /**
     * Decodes a line. If this is an "aka" line with a known charset at the end of line,
     * the title part is decoded with the given charset. The returned line should always
     * be unicode and ready for further processing without charset hassles.
     *
     * @param line
     *            Line to be decoded
     * @return Decoded line
     */
    private String decodeLine(byte[] line) {
        String decoded = new String(line, DEFAULT_CHARSET);

        Matcher m = AKA_PATTERN.matcher(decoded);
        if (!m.matches()) {
            // It's not an 'aka' line and/or default encoded. Just return it.
            return decoded;
        }

        Charset charset = Charset.forName(latinToIso(m.group(1)));

        int start = "   (aka ".length();
        int end = start;
        int cnt = 0;
        for (int ix = start; ix < line.length; ix++) {
            if (line[ix] == '(') {
                cnt++;
            } else if (line[ix] == ')') {
                cnt--;
                if (cnt < 0) {
                    end = ix - 1;
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(new String(line, 0, start, DEFAULT_CHARSET));
        sb.append(new String(line, start, end - start, charset));
        sb.append(new String(line, end, line.length - end, DEFAULT_CHARSET));
        return sb.toString();
    }

}
