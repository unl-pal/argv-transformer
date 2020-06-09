package full;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * Driver for full framework. Parse command line arguments and start the analysis.
 * 
 * @author mariapaquin
 *
 */

public class Driver {

	private final static String DEFAULT_MIN_LOC = "100";
	private final static String DEFAULT_MAX_LOC = "10000";
	private final static String DEFAULT_PROJECT_COUNT = "1";
	private final static String DEFAULT_MIN_EXPR = "1";
	private final static String DEFAULT_MIN_IFSTMT = "0";
	private final static String DEFAULT_MIN_PARAMS = "0";

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
			String type = props.getProperty("type");
			int minExpr = Integer.parseInt(props.getProperty("minExpr", DEFAULT_MIN_EXPR));
			//in the future we can add exclusion right now it is either required or don't care how many
			int minIfStmt = Integer.parseInt(props.getProperty("minIfStmt", DEFAULT_MIN_IFSTMT));
			//Minimum number of parameters of that type
			int minParams = Integer.parseInt(props.getProperty("minParams", DEFAULT_MIN_PARAMS));
			//How to compile: to suite SPF(with call to Debug) or just regular (with call to Random) to
			//over-approximate unresolved expression, e.g., method calls, field access
			String target = props.getProperty("target");
			//The final static compilations are defined in full.Main class
			//Main.start(filename, projectCount, minLoc, maxLoc, debugLevel, downloadDir, benchmarkDir);
			Main.start(filename, projectCount, minLoc, maxLoc, debugLevel, downloadDir, benchmarkDir,
					type, minExpr, minIfStmt, minParams, target);
		} catch (IOException exp) {
			System.out.println("Invalid configuration file.");
			System.exit(1);
		}
	}
}