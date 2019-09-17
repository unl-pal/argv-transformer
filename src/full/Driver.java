package full;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Driver for MainAnalysis. Parse command line arguments and start the analysis.
 * 
 * @author mariapaquin
 *
 */

public class Driver {

	private final static String DEFAULT_MIN_LOC = "100";
	private final static String DEFAULT_MAX_LOC = "10000";
	private final static String DEFAULT_PROJECT_COUNT = "1";

	public static void main(String[] args) {
		File configFile = new File("config.properties");
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);

			int projectCount = Integer.parseInt(props.getProperty("projectCount", DEFAULT_PROJECT_COUNT));
			int minLoc = Integer.parseInt(props.getProperty("minLoc", DEFAULT_MIN_LOC));
			int maxLoc = Integer.parseInt(props.getProperty("maxLoc", DEFAULT_MAX_LOC));
			String downloadDir = props.getProperty("downloadDir");
			String benchmarkDir = props.getProperty("benchmarkDir");
			String filename = props.getProperty("csv");
			int debugLevel = Integer.parseInt(props.getProperty("debugLevel", "-1"));

			Main.start(filename, projectCount, minLoc, maxLoc, debugLevel, downloadDir, benchmarkDir);
		} catch (IOException exp) {
			System.out.println("Invalid configuration file.");
			System.exit(1);
		}
	}
}