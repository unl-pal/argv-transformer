package filter;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import filter.file.FileFilter;

/**
 * Filter a directory full of repositories.
 *
 * input: database, a directory of repositories
 * output: suitablePrgms, a directory containing only the java files
 * suitable for symbolic execution found in the database directory
 *
 * @author mariapaquin
 */
public class Main {
	
	private final static String DEFAULT_MIN_EXPR = "0";
	private final static String DEFAULT_MIN_IFSTMT = "0";
	private final static String DEFAULT_MIN_PARAMS = "0";
	private final static String DEFAULT_TYPE = "I";
			
	public static void main(String[] args) throws IOException {
		String inputPath = "database";
		String outputPath = "suitablePrgms";
 
		if (args.length == 2) {
			inputPath = args[0];
			outputPath = args[1];
		}
		//read the rest of config properties
		int minExpr = 0;
		int minIfStmt = 0;
		int minParams = 0;
		String type = DEFAULT_TYPE;
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"); 
			Properties props = new Properties();
			props.load(is);
			type = props.getProperty("type");
			minExpr = Integer.parseInt(props.getProperty("minExpr", DEFAULT_MIN_EXPR));
			//in the future we can add exclusion right now it is either required or don't care how many
			minIfStmt = Integer.parseInt(props.getProperty("minIfStmt", DEFAULT_MIN_IFSTMT));
			//Minimum number of parameters of that type
			minParams = Integer.parseInt(props.getProperty("minParams", DEFAULT_MIN_PARAMS));
			
		} catch (IOException exp) {
			System.out.println("Invalid configuration file.");
			System.exit(1);
		}
		
		System.out.println(type + " " + minExpr + " " + minIfStmt + " " + minParams);

		File tmpDir = Files.createTempDirectory("paclab-filter").toFile();
		File tempCopy = new File(tmpDir, "temp");
		
		File srcDir = new File(inputPath);
		File destDir = new File(outputPath);
		
		if (tempCopy.exists()) {
			try {
				FileUtils.forceDelete(tempCopy);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileUtils.forceMkdir(tempCopy);
		
		if (destDir.exists()) {
			try {
				FileUtils.forceDelete(destDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileUtils.forceMkdir(destDir);
		

		try {
			FileUtils.copyDirectory(srcDir, tempCopy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
 
		FileFilter filter = new FileFilter(tempCopy,type, minExpr, minIfStmt, minParams);
		System.out.println("Done filtering");

		filter.collectJavaFiles();
		filter.collectSuitableFiles();

		ArrayList<File> files = filter.getSuitableFiles();
		

		for (File f : files) {
			String newPath = f.getAbsolutePath().replace(tempCopy.getAbsolutePath(), destDir.getAbsolutePath());

			File destinationFile = new File(newPath);
			destinationFile.getParentFile().mkdirs();
			try {
				if (destinationFile.exists()) {
					destinationFile.delete();
				}
				FileUtils.copyFile(f, destinationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
