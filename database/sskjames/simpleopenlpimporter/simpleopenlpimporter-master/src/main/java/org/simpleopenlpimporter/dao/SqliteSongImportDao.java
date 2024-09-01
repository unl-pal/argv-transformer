package org.simpleopenlpimporter.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.UnhandledException;
import org.simpleopenlpimporter.domain.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqliteSongImportDao implements ISongImportDao
{
	private final static Logger logger = LoggerFactory
			.getLogger(SqliteSongImportDao.class);
}
