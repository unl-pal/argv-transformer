package mainFullFramework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.classfile.Method;

import org.apache.bcel.generic.ClassGenException;

import download.Downloader;
import download.GitProject;
import filter.fileFilter.GeneralFileInfo;
import filter.fileFilter.SuitableFileFilter;
import filter.fileFilter.SymbolicSuitableMethodFinder;
import gov.nasa.jpf.util.FileUtils;
import jpf.EmbeddedJPF;
import jpf.ProgramUnderTest;
import logging.Logger;
import sourceAnalysis.AnalyzedFile;
import transform.Transformer;

/**
 *
 * Given a CSV of GitHub repositories (as gathered by RepoReaper),
 * this program will select suitable repositories, download them,
 * search for classes containing SPF-suitable methods, and transform 
 * suitable classes into compilable, benchmark programs. The resulting 
 * benchmarks are run with Java PathFinder. 
 * 
 * @author mariapaquin
 *
 */

public class MainAnalysis {
	
	private static boolean secondCompile = false;
	private	static FileWriter fileWriter;
	private static PrintWriter printWriter;
	private static int totalNumFiles;
	private static int totalNumMethods;
	private static int totalSpfSuitableMethods;
	private static int compilableSpfSuitableMethodCount;
	private static int compilableAfterTransformSpfSuitableMethodCount;

	
	public static void start(String filename, int projectCount, int minLoc, int maxLoc, int debugLevel,
			String downloadDir, String benchmarkDir) throws IOException {
		
		fileWriter = new FileWriter("./CompilationIssues.txt");
		printWriter = new PrintWriter(fileWriter);
		
		totalNumFiles = 0;
		totalNumMethods = 0;
		totalSpfSuitableMethods = 0;
		compilableSpfSuitableMethodCount = 0;
		compilableAfterTransformSpfSuitableMethodCount = 0;
		
		Logger.defaultLogger.setDebugLevel(debugLevel);
		Logger.defaultLogger.enterContext("MAIN");
		
		Downloader downloader = new Downloader(filename);
		
		downloader.createProjectDatabase();
		downloader.filterProjects(minLoc, maxLoc);
		downloader.downloadProjects(projectCount, downloadDir);

		long startTime = System.currentTimeMillis();
		
		List<GitProject> projects = downloader.getDownloadedGitProjects();
		
		for (GitProject project : projects) {
			project.collectFilesInProject();
		}
		
		GeneralFileInfo generalInfo = new GeneralFileInfo(projects);
		totalNumFiles = generalInfo.countFiles();
		totalNumMethods = generalInfo.countMethods();

		Logger.defaultLogger.enterContext("FILTER");

		SuitableFileFilter filter = new SuitableFileFilter(projects);
		filter.findSuitableFiles();
		
		totalSpfSuitableMethods = filter.getSuitableMethodCount();

		ArrayList<File> spfSuitableFiles = filter.getSuitableFiles();

		Logger.defaultLogger.exitContext("FILTER");
		
		ArrayList<File> copiedFiles = copyFiles(spfSuitableFiles, downloadDir, "temp");
		ArrayList<File> successfulCompiles = new ArrayList<File>();
		ArrayList<File> unsuccessfulCompiles = new ArrayList<File>();
				
		for(File file: copiedFiles) {
			Logger.defaultLogger.enterContext("COMPILING");
			
			boolean success = compile(file);
			if(success) {
				compilableSpfSuitableMethodCount += countCompilableSpfSuitableMethodsInFile(file);
				successfulCompiles.add(file);
			}else {
				unsuccessfulCompiles.add(file);
			}
			Logger.defaultLogger.exitContext("COMPILING");
		}
				
		secondCompile = true;
		
		Transformer transformer = new Transformer(unsuccessfulCompiles);
		transformer.transformFiles();

	
		ArrayList<File> successfulCompilesAfterTransform = new ArrayList<File>();
		ArrayList<File> unsuccessfulCompilesAfterTransform = new ArrayList<File>();
				
		for(File file: copiedFiles) {
			Logger.defaultLogger.enterContext("SECOND COMPILING");
			boolean success = compile(file);
			if(success) {
				compilableAfterTransformSpfSuitableMethodCount += countCompilableSpfSuitableMethodsInFile(file);
				successfulCompilesAfterTransform.add(file);
			}else {
				unsuccessfulCompilesAfterTransform.add(file);
			}
			Logger.defaultLogger.exitContext("SECOND COMPILING");
		}
		
		long endTime = System.currentTimeMillis();
		
		ArrayList<File> benchmarks = copyFiles(successfulCompilesAfterTransform, "temp", benchmarkDir);

		Logger.defaultLogger.enterContext("JPF");

	//	runJPF(successfulCompilesAfterTransform);
		
		Logger.defaultLogger.exitContext("JPF");

		System.out.println(""
				+ "\nTotal files: " + totalNumFiles
				+ "\nTotal methods: " + totalNumMethods
				+ "\nFiles suitable for SPF: " + spfSuitableFiles.size()
				+ "\nMethods suitable for SPF: " + totalSpfSuitableMethods
				+ "\nFiles with successful compile: " + successfulCompiles.size()
				+ "\nMethods suitable for SPF in successfully compiled classes: " + compilableSpfSuitableMethodCount
				+ "\nFiles with successful compile after transform: " + successfulCompilesAfterTransform.size()
				+ "\nMethods suitable for symbolic execution: " + compilableAfterTransformSpfSuitableMethodCount
				+ "\n\nTime: " + (endTime - startTime)
				);
		
		Logger.defaultLogger.exitContext("MAIN");

		printWriter.close();
	}
	
	private static int countCompilableSpfSuitableMethodsInFile(File file) {
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
//				System.out.println(file.getAbsolutePath());
				ProgramUnderTest sut = new ProgramUnderTest(file);
				String className = sut.getClassName();

				sut.insertMain();

				Method[] methods = sut.getMethods();
				for (Method method : methods) {
//					System.out.println(method.getName());
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
					System.out.println(className);
		//			EmbeddedJPF.runJPF(className, fullMethodName, numIntArgs, boundSearch);

					compilableAfterTransformSpfSuitableMethodCount++;
				}
			} catch (ClassGenException e) {
				e.printStackTrace();
				continue;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
				continue;
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}

	}

	/**
	 * TEMPORARY Copy files that are suitable for SPF analysis to benchmark program
	 * directory. Keep the directory structure until we change the names of the
	 * classes, as some class names might be the same.
	 * 
	 * @param spfSuitableFiles
	 * @param downloadToDir 
	 * @param tempDir 
	 * @param directory
	 * @return 
	 */
	private static ArrayList<File> copyFiles(ArrayList<File> spfSuitableFiles, String fromDir, String toDir) {

		ArrayList<File> copiedFiles = new ArrayList<File>();
		for (File file : spfSuitableFiles) {
			String newPath = file.getAbsolutePath().replace(fromDir, toDir);
			File destinationFile = new File(newPath);
			destinationFile.getParentFile().mkdirs();
			try {
				if(destinationFile.exists()) {
					destinationFile.delete();
				}
				FileUtils.copyFile(file, destinationFile.getParentFile());
				copiedFiles.add(destinationFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copiedFiles;
	}
	

	private static boolean compile(File file) throws IOException {
		File buildDir = new File("build");

		if (buildDir.exists()) {
			try {
				org.apache.commons.io.FileUtils.forceDelete(buildDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		org.apache.commons.io.FileUtils.forceMkdir(buildDir);

		// TODO: check if build exists, if it doesn't, create it as a directory
		String command = "javac -g -d build/ " + file;

//		if(secondCompile) {
			printWriter.println("-----------------------------------------------------------------------------------------------");
			printWriter.println(command);

//		}
		boolean success = false;
		try {
			Process pro = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", command });
			pro.waitFor();
			if(secondCompile) {
				printCompileExitStatus(command + " stdout:", pro.getInputStream());
				printCompileExitStatus(command + " stderr:", pro.getErrorStream());
			}
	//		Logger.defaultLogger.logln("Compiled file " + file.getPath() +" with exit status " + pro.exitValue(), 1);
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
