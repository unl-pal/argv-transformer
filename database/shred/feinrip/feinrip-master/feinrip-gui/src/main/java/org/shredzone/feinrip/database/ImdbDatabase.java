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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shredzone.feinrip.gui.ErrorDialog;

/**
 * Manages a movie title database.
 * <p>
 * Internally, the database is stored via HSQLDB in a file in the user's home directory.
 *
 * @author Richard "Shred" Körber
 */
public class ImdbDatabase {

    private static final int QUERY_TIMEOUT = 30;
    private static final Pattern TITLE_PATTERN = Pattern.compile("(\\s+\\(aka\\s+)?(.*?)\\s+\\((\\d{4})\\).*");

    private static ImdbDatabase instance;

    private Connection connection;
    private boolean ro;

}
