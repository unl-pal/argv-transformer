package org.simpleopenlpimporter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.simpleopenlpimporter.dao.SqliteSongImportDao;
import org.simpleopenlpimporter.domain.Song;

public class SongsImporterTest
{
	private File file = new File("src/test/resources/songs/song1.txt");
	private SongsImporter songsImporter = new SongsImporter() {
		public org.simpleopenlpimporter.dao.ISongImportDao getSongImportDao()
		{
			return new SqliteSongImportDao() {
				protected String getDbPath()
				{
					return "src/test/resources/db/songs.sqlite";
				};
			};
		};
	};
}
