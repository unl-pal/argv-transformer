/*
 * feinrip
 *
 * Copyright (C) 2017 Richard "Shred" Körber
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
package org.shredzone.feinrip.system;

import java.io.File;
import java.io.IOException;

import org.shredzone.feinrip.progress.LogConsumer;
import org.shredzone.feinrip.progress.PercentConsumer;
import org.shredzone.feinrip.progress.ProgressMeter;
import org.shredzone.feinrip.util.Command;

/**
 * Invoker of an external preprocessor script.
 *
 * @author Richard "Shred" Körber
 */
public class PreprocessorInvoker {

    private final File script;
    private File vobFile;
    private String jsonProcessData;

}
