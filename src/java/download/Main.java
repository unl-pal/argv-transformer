package download;

import util.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.List;

/**
 * UPDATE THIS LATER 
* Given csv with project list download repos for filtering and transforming
 */

public class Main {

	private final static String DEFAULT_MIN_LOC = "100";
	private final static String DEFAULT_MAX_LOC = "10000";
	private final static String DEFAULT_PROJECT_COUNT = "s";
	private final static String DEFAULT_DEBUG_LEVEL = "-1";

	private static int debugLevel;

	public static String dest = "database";

	public static void main(String[] args) throws IOException {
		String outputPath = "";

		if (args.length == 1) {
			outputPath = args[0];
		}

		File configFile = new File("config.properties");

		int minLoc = 1;
		int maxLoc = 1;
		int projectCount = 1;
		String filename = "";
		String downloadDir = "";

		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			minLoc = Integer.parseInt(props.getProperty("minLoc", DEFAULT_MIN_LOC));
			maxLoc = Integer.parseInt(props.getProperty("maxLoc", DEFAULT_MAX_LOC));
			projectCount = Integer.parseInt(props.getProperty("projectCount", DEFAULT_PROJECT_COUNT));
			filename = props.getProperty("csv");
			downloadDir = props.getProperty("downloadDir");
			debugLevel = Integer.parseInt(props.getProperty("debugLevel", DEFAULT_DEBUG_LEVEL));
		} catch (IOException exp) {
			System.out.println("Invalid configuration file.");
			System.exit(1);
		}
		
		Logger.defaultLogger.setDebugLevel(debugLevel);
		Logger.defaultLogger.enterContext("MAIN");

		Downloader downloader = new Downloader(filename);
		downloader.createProjectDatabase();
		downloader.filterProjects(minLoc, maxLoc);
		downloader.downloadProjects(projectCount, downloadDir);

		List<GitProject> projects = downloader.getGitProjects();

		for (GitProject project : projects) {
			project.collectFilesInProject();
		}
	}
}
