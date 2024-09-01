package org.simpleopenlpimporter.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.simpleopenlpimporter.dao.SqliteSongImportDao;
import org.simpleopenlpimporter.domain.Song;

public class SqliteSongImportDaoTest
{
	private String dbPath = "src/test/resources/db/songs.sqlite";
	private SqliteSongImportDao sqliteDao = new SqliteSongImportDao() {
		protected String getDbPath()
		{
			return dbPath;
		};
	};
}
