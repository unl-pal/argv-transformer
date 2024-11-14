/*
 * feinrip
 *
 * Copyright (C) 2014 Richard "Shred" Körber
 *   https://github.com/shred/feinrip
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.shredzone.feinrip.lsdvd;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shredzone.feinrip.lsdvd.DvdTitleSet.Aspect;

/**
 * Reads IFO and BUP files of a DVD and returns a basic overview of the DVD structure.
 * <p>
 * It is a simpler, but less buggy replacement for the <code>lsdvd</code> tool.
 *
 * @author Richard "Shred" Körber
 * @see <a href="http://stnsoft.com/DVD/index.html">DVD-Video Information</a>
 */
public class IfoReader {

    private static LsdvdLogger LOG = new LsdvdLogger(IfoReader.class);

    private final List<DvdTitle> titles = new ArrayList<>();



}
