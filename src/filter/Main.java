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
 * output: suitablePrgms, a directory containing only the java files
 * suitable for symbolic execution found in the database directory
 *
 * @author mariapaquin
 */
public class Main {
	public static void main(String[] args) {
		String inputPath = "database";
		String outputPath = "suitablePrgms";
 
		if (args.length == 2) {
			inputPath = args[0];
			outputPath = args[1];
		}

		File inDir = new File(inputPath);
		File outDir = new File(outputPath);
 
		FileFilter filter = new FileFilter(inDir);

		filter.collectJavaFiles();
		filter.collectSuitableFiles();

		ArrayList<File> files = filter.getSuitableFiles();

		for (File f : files) {
			String newPath = f.getAbsolutePath().replace(inDir.getAbsolutePath(), outDir.getAbsolutePath());

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
