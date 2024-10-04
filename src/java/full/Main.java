package full;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import download.Downloader;
import download.GitProject;
import filter.file.GeneralFileInfo;
import filter.file.FileFilter;
import filter.file.SymbolicSuitableMethodFinder;
import logging.Logger;
import sourceAnalysis.AnalyzedFile;
import transform.Transformer;

/**
 *
 * Given a CSV of GitHub repositories (as gathered by RepoReaper), this program
 * will select suitable repositories, download them, search for classes
 * containing SPF-suitable methods, and transform suitable classes into
 * compilable, benchmark programs. 
 * 
 * @author mariapaquin
 *
 */

public class Main {

	private static boolean secondCompile = false;
	private static FileWriter fileWriter;
	private static PrintWriter printWriter;
	private static int totalNumFiles;
	private static int totalNumMethods;
	private static int totalSuitableMethods;
	private static int compilableSpfSuitableMethodCount;
	private static int compilableAfterTransformSpfSuitableMethodCount;
	
	private final static String SPF_COMPILE = "javac -g -d bin/ -cp .:/Users/elenasherman/git/jpf-symbc/build/classes/ ";
	private final static String DEFAULT_COMPILE = "javac -g -d bin/ ";
	private static String COMPILE = "";

	/**
	 * @param filename
	 * @param projectCount
	 * @param minLoc
	 * @param maxLoc
	 * @param debugLevel
	 * @param downloadDir
	 * @param benchmarkDir
	 * @param minIfStmt 
	 * @param ifStmt 
	 * @param minExpr 
	 * @param type 
	 * @throws IOException
	 */
	public static void start(String filename, int projectCount, int minLoc, int maxLoc, int debugLevel,
			String downloadDir, String benchmarkDir, String type, int minExpr, int minIfStmt, int minParams, String target) throws IOException {

		fileWriter = new FileWriter("./CompilationIssues.txt");
		printWriter = new PrintWriter(fileWriter);
		
		totalNumFiles = 0;
		totalNumMethods = 0;
		totalSuitableMethods = 0;
		compilableSpfSuitableMethodCount = 0;
		compilableAfterTransformSpfSuitableMethodCount = 0;
		
		switch(target) {
		case "SPF" : COMPILE = SPF_COMPILE;
		break;
		default: COMPILE = DEFAULT_COMPILE;
		}

		File benchmarks = new File(benchmarkDir);

		if (benchmarks.exists()) {
			try {
				FileUtils.forceDelete(benchmarks);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File suitablePrgms = new File("suitablePrgms");

		if (suitablePrgms.exists()) {
			try {
				FileUtils.forceDelete(suitablePrgms);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Logger.defaultLogger.setDebugLevel(debugLevel);
		Logger.defaultLogger.enterContext("MAIN");

		Downloader downloader = new Downloader(filename);

		downloader.createProjectDatabase();
		downloader.filterProjects(minLoc, maxLoc);
		downloader.downloadProjects(projectCount, downloadDir);

		long startTime = System.currentTimeMillis();

		List<GitProject> projects = downloader.getGitProjects();

		for (GitProject project : projects) {
			project.collectFilesInProject();
		}

		GeneralFileInfo generalInfo = new GeneralFileInfo(projects);
		totalNumFiles = generalInfo.countFiles();
		totalNumMethods = generalInfo.countMethods();

		Logger.defaultLogger.enterContext("FILTER");

		FileFilter filter = new FileFilter(projects,type,  minExpr, minIfStmt, minParams);
		filter.collectSuitableFilesInProjectList();

		totalSuitableMethods = filter.getSuitableMethodCount();

		ArrayList<File> suitableFiles = filter.getSuitableFiles();

		Logger.defaultLogger.exitContext("FILTER");

		ArrayList<File> copiedFiles = copyFiles(suitableFiles, downloadDir, "suitablePrgms");
//		ArrayList<File> successfulCompiles = new ArrayList<File>();
//		ArrayList<File> unsuccessfulCompiles = new ArrayList<File>();
//		
//		for (File file : copiedFiles) {
//			Logger.defaultLogger.enterContext("COMPILING");
//
//			boolean success = compile(file);
//			if (success) {
//				compilableSpfSuitableMethodCount += countSpfSuitableMethods(file);
//				successfulCompiles.add(file);
//			} else {
//				unsuccessfulCompiles.add(file);
//			}
//			Logger.defaultLogger.exitContext("COMPILING");
//		}
//
//		secondCompile = true;
//
//		Transformer transformer = new Transformer(unsuccessfulCompiles, target);
//		transformer.transformFiles();
//
//		ArrayList<File> successfulCompilesAfterTransform = new ArrayList<File>();
//		ArrayList<File> unsuccessfulCompilesAfterTransform = new ArrayList<File>();
//		
//		for (File file : copiedFiles) {
//			Logger.defaultLogger.enterContext("RECOMPILING");
//			boolean success = compile(file);
//			if (success) {
//				compilableAfterTransformSpfSuitableMethodCount += countSpfSuitableMethods(file);
//				successfulCompilesAfterTransform.add(file);
//			} else {
//				unsuccessfulCompilesAfterTransform.add(file);
//			}
//			Logger.defaultLogger.exitContext("RECOMPILING");
//		}
//
//		long endTime = System.currentTimeMillis();
//
//		copyFiles(successfulCompilesAfterTransform, "suitablePrgms", benchmarkDir);
//
//		System.out.println("" + "\nTotal files: " + totalNumFiles + "\nTotal methods: " + totalNumMethods
//				+ "\nFiles suitable for SPF: " + suitableFiles.size() + "\nMethods suitable for SPF: "
//				+ totalSuitableMethods + "\nFiles with successful compile: " + successfulCompiles.size()
//				+ "\nMethods suitable for SPF in successfully compiled classes: " + compilableSpfSuitableMethodCount
//				+ "\nFiles with successful compile after transform: " + successfulCompilesAfterTransform.size()
//				+ "\nMethods suitable for symbolic execution: " + compilableAfterTransformSpfSuitableMethodCount
//				+ "\n\nTime: " + (endTime - startTime));
//
//		Logger.defaultLogger.exitContext("MAIN");

		printWriter.close();
	}

	/**
	 * Count the number of SPF suitable methods in the file.
	 * 
	 * @param file File.
	 * @return Count of SPF suitable methods.
	 */
	private static int countSpfSuitableMethods(File file) {
		int spfSuitableMethods = 0;
		try {
			SymbolicSuitableMethodFinder finder = new SymbolicSuitableMethodFinder(file);
			finder.analyze();
			AnalyzedFile af = finder.getAnalyzedFile();
			spfSuitableMethods = af.getSpfSuitableMethodCount();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spfSuitableMethods;
	}

	/**
	 * Copy a list of files from a source directory to a target directory.
	 * 
	 * @param files Files to copy.
	 * @param fromDir Source directory.
	 * @param toDir Target directory.
	 * @return The list of copied files (updated paths).  
	 */
	private static ArrayList<File> copyFiles(ArrayList<File> files, String fromDir, String toDir) {

		ArrayList<File> copiedFiles = new ArrayList<File>();
		for (File file : files) {
			String newPath = file.getAbsolutePath().replace(fromDir, toDir);

			File destinationFile = new File(newPath);
			destinationFile.getParentFile().mkdirs();
			try {
				if (destinationFile.exists()) {
					destinationFile.delete();
				}
				FileUtils.copyFile(file, destinationFile);
				copiedFiles.add(destinationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copiedFiles;
	}

	/**
	 * Compile the file, putting the .class file in build directory if successful.
	 * 
	 * @param file File to compile
	 * @return true if the file compiled successfully, false otherwise.
	 * @throws IOException
	 */
	private static boolean compile(File file) throws IOException {

		String command = COMPILE + file;

		boolean success = false;
		try {
			Process pro = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", command });
			pro.waitFor();

			// - during development -
			// keeping track of why files still would not compile after transformation
			if (secondCompile) {
				printCompileExitStatus(command + " stdout:", pro.getInputStream());
				printCompileExitStatus(command + " stderr:", pro.getErrorStream());
			}

			Logger.defaultLogger.logln("Compiled file " + file.getPath() + " with exit status " + pro.exitValue(), 1);

			if (pro.exitValue() == 0) {
				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}

	private static void printCompileExitStatus(String cmd, InputStream ins) throws Exception {
		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			printWriter.println(line);
		}
	}
}
