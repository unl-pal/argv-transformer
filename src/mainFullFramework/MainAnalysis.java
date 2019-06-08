package mainFullFramework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.bcel.classfile.Method;

import org.apache.bcel.generic.ClassGenException;
import org.apache.commons.io.FileUtils;

import download.Downloader;
import download.GitProject;
import filter.fileFilter.GeneralFileInfo;
import filter.fileFilter.SuitableFileFilter;
import filter.fileFilter.SymbolicSuitableMethodFinder;
import jpf.EmbeddedJPF;
import jpf.ProgramUnderTest;
import logging.Logger;
import sourceAnalysis.AnalyzedFile;
import transform.Transformer;

/**
 *
 * Given a CSV of GitHub repositories (as gathered by RepoReaper), this program
 * will select suitable repositories, download them, search for classes
 * containing SPF-suitable methods, and transform suitable classes into
 * compilable, benchmark programs. The resulting benchmarks are run with Java
 * PathFinder.
 * 
 * @author mariapaquin
 *
 */

public class MainAnalysis {

	private static PrintWriter printWriter;
	
	public static void start(String filename, int projectCount, int minLoc, int maxLoc, int debugLevel,
			String downloads, String benchmarks) throws IOException {

		printWriter = new PrintWriter(new FileWriter("./CompilationIssues.txt"));

		int totalFiles = 0;
		int totalMethods = 0;
		int symbSuitableMethods = 0;
		int symbMethods_AsIs = 0;
		int symbMethods_AfterTransform = 0;

		Logger.defaultLogger.setDebugLevel(debugLevel);
		Logger.defaultLogger.enterContext("MAIN");

		File benchmarkDir = new File(benchmarks);
		File downloadDir = new File(downloads);

		Downloader downloader = new Downloader(filename);

		downloader.createProjectDatabase();
		downloader.filterProjects(minLoc, maxLoc);
		downloader.downloadProjects(projectCount, downloadDir);

		if (benchmarkDir.exists()) {
			FileUtils.forceDelete(benchmarkDir);
		}
		FileUtils.forceMkdir(benchmarkDir);

		try {
			FileUtils.copyDirectory(downloadDir, benchmarkDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<GitProject> projects = downloader.getDownloadedGitProjects();

		for (GitProject project : projects) {
			project.collectFilesInProject();
		}

		GeneralFileInfo generalInfo = new GeneralFileInfo(projects);
		totalFiles = generalInfo.countFiles();
		totalMethods = generalInfo.countMethods();

		SuitableFileFilter filter = new SuitableFileFilter();
		filter.removeUnsuitableFiles(benchmarkDir);

		symbSuitableMethods = filter.getSuitableMethodCount();

		ArrayList<File> spfSuitableFiles = filter.getSuitableFiles();

		ArrayList<File> successfulCompiles = new ArrayList<File>();
		ArrayList<File> unsuccessfulCompiles = new ArrayList<File>();

		File buildDir = new File("build");

		if (buildDir.exists()) {
			try {
				FileUtils.forceDelete(buildDir);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileUtils.forceMkdir(buildDir);

		Iterator<File> file_itr = FileUtils.iterateFiles(benchmarkDir, new String[] { "java" }, true);
		while (file_itr.hasNext()) {
			File file = (File) file_itr.next();
			boolean success = compile(file);
			if (success) {
				symbMethods_AsIs += countSymbolicSuitableMethods(file);
				successfulCompiles.add(file);
			} else {
				unsuccessfulCompiles.add(file);
			}
		}

		Transformer transformer = new Transformer(unsuccessfulCompiles);
		transformer.transformFiles();

		ArrayList<File> successfulCompilesAfterTransform = new ArrayList<File>();
		ArrayList<File> unsuccessfulCompilesAfterTransform = new ArrayList<File>();

		file_itr = FileUtils.iterateFiles(benchmarkDir, new String[] { "java" }, true);

		while (file_itr.hasNext()) {
			File file = (File) file_itr.next();
			boolean success = compile(file);
			if (success) {
				symbMethods_AfterTransform += countSymbolicSuitableMethods(file);
				successfulCompilesAfterTransform.add(file);
			} else {
				try {
					Files.delete(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				unsuccessfulCompilesAfterTransform.add(file);
			}
		}

		// runJPF(successfulCompilesAfterTransform);

		System.out.println("" + "\nTotal files: " + totalFiles + "\nTotal methods: " + totalMethods
				+ "\nFiles suitable for SPF: " + spfSuitableFiles.size() + "\nMethods suitable for SPF: "
				+ symbSuitableMethods + "\nFiles with successful compile: " + successfulCompiles.size()
				+ "\nMethods suitable for SPF in successfully compiled classes: " + symbMethods_AsIs
				+ "\nFiles with successful compile after transform: " + successfulCompilesAfterTransform.size()
				+ "\nMethods suitable for symbolic execution: " + symbMethods_AfterTransform);

		Logger.defaultLogger.exitContext("MAIN");

		printWriter.close();
	}

	private static int countSymbolicSuitableMethods(File file) {
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
	 * for each file in successfulCompilesAfterTransform - determine which methods
	 * are suitable for SPF - for each suitable method - insert/replace main - count
	 * the number of parameters - insert a call to the method from main - check for
	 * loops - create jpf config file - run jpf config file
	 */
	private static void runJPF(ArrayList<File> files) {
		for (File file : files) {
			try {
				// System.out.println(file.getAbsolutePath());
				ProgramUnderTest sut = new ProgramUnderTest(file);
				String className = sut.getClassName();

				sut.insertMain();

				Method[] methods = sut.getMethods();
				for (Method method : methods) {
					// System.out.println(method.getName());
					String fullMethodName = className + "." + method.getName();

					// skip main
					if (method.getName().equals("main") || method.getName().equals("<init>")
							|| method.getName().equals("<clinit>") || method.getName().contains("access$")) {
						continue;
					}

					// check suitability of method for SPF analysis
					int numArgs = sut.getNumArgs(method);
					int numIntArgs = sut.getNumIntArgs(method);

					if (numIntArgs == 0 || numIntArgs != numArgs) {
						continue;
					}

					boolean boundSearch = sut.checkForLoops(method.getName());

					sut.insertMethodCall(method, numIntArgs);

					// EmbeddedJPF.runJPF(className, fullMethodName, numIntArgs, boundSearch);

				}
			} catch (ClassGenException e) {
				System.out.println("jpf exception");

				// TODO figure out why this is happening
				continue;
			} catch (ClassNotFoundException e) {
				System.out.println("jpf exception");
				// TODO Auto-generated catch block
				continue;
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

	}

	private static boolean compile(File file) {
		String command = "javac -d build/ " + file;

		boolean success = false;
		try {
			Process pro = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", command });
			pro.waitFor();
			// printCompileExitStatus(command + " stdout:", pro.getInputStream());
			// printCompileExitStatus(command + " stderr:", pro.getErrorStream());
			// Logger.defaultLogger.logln("Compiled file " + file.getPath() +" with exit
			// status " + pro.exitValue(), 1);
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
