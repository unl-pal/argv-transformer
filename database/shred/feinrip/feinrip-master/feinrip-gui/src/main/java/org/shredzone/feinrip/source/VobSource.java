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

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.shredzone.feinrip.gui.ErrorDialog;
import org.shredzone.feinrip.model.Audio;
import org.shredzone.feinrip.model.Palette;
import org.shredzone.feinrip.model.PaletteType;
import org.shredzone.feinrip.model.Subtitle;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.system.EitAnalyzer;
import org.shredzone.feinrip.system.StreamUtils;
import org.shredzone.feinrip.system.VobAnalyzer;
import org.shredzone.feinrip.util.VobsubIndex;
import org.shredzone.feinrip.util.VobsubIndex.Setting;

/**
 * A {@link Source} for a single vob file.
 *
 * @author Richard "Shred" Körber
 */
public class VobSource extends AbstractSource {

    private File vobFile;
    private File eitFile;
    private PaletteType palette = PaletteType.DEFAULT;
    private Palette customPalette;

}
