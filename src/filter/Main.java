package filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import filter.file.FileFilter;

/**
 * Filter a directory full of repositories.
 * 
 * input: database, a directory of repositories
 * output: benchmarks, a directory containing only the java files 
 * suitable for symbolic execution found in the database directory
 * 
 * @author mariapaquin
 *
 */
public class Main {

	public static void main(String[] args) {
		File database = new File("database");

		FileFilter filter = new FileFilter(database);

		filter.collectJavaFiles();
		filter.collectSymbolicSuitableFiles();

		ArrayList<File> files = filter.getSuitableFiles();

		for (File f : files) {
			String newPath = f.getAbsolutePath().replace("database", "benchmarks");

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
