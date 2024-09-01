package org.simpleopenlpimporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.UnhandledException;
import org.simpleopenlpimporter.dao.ISongImportDao;
import org.simpleopenlpimporter.dao.SqliteSongImportDao;
import org.simpleopenlpimporter.domain.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SongsImporter implements ISongsImporter
{
	private final static Logger logger = LoggerFactory
			.getLogger(SongsImporter.class);
}
