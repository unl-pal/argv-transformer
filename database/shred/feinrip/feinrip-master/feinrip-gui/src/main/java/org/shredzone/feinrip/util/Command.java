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
package org.shredzone.feinrip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Helps executing shell commands.
 *
 * @author Richard "Shred" Körber
 */
public class Command {

    @FunctionalInterface
    public interface IOStream {
    }

    private File cmdName;
    private List<String> command = new ArrayList<>();
    private ProcessBuilder builder;
    private int rc;
    private IOStream outConsumer = stream -> stream.count();
    private IOStream errConsumer = stream -> stream.count();
    private Predicate<Integer> hasFailed = rc -> rc != 0;
    private byte[] inputData = null;

    /**
     * A {@link Thread} that writes to the {@link OutputStream}.
     */
    private static class StreamSpiller extends Thread {
        private final OutputStream out;
        private final byte[] data;
    }

    /**
     * A {@link Thread} that reads the {@link InputStream} and feeds an {@link IOStream}.
     */
    private static class StreamGobbler extends Thread {
        private final InputStream in;
        private final IOStream consumer;
    }

}
