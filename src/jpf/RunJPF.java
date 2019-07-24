package jpf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGenException;
import org.apache.commons.io.FileUtils;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import logging.Logger;

/**
 * Class used to create a configuration file and run JPF.
 * 
 * @author mariapaquin
 *
 */
public class RunJPF {
	private static FileWriter writer;
	private static FileWriter writerGreen;
	private static FileWriter errorLog;
	private static String projectName;
	private static String packageName;
	private static String className;
	private static String methodName;

	/**
	 * Create a configuration file for the given class and method, then run JPF.
	 * 
	 * @param fullClassName
	 * @param fullMethodName
	 * @param numArgs
	 * @param boundSearch
	 * @throws IOException
	 */
	public static void runJPF(String fullClassName, String fullMethodName, int numArgs, boolean boundSearch) throws IOException {

		String[] jpfArgs = { "-log" };
		Config config = JPF.createConfig(jpfArgs);

		config.setProperty("classpath", "bin");
		config.setProperty("target", fullClassName);
		config.setProperty("search.multiple_errors", "true");

		String symArgs = "(sym";
		for (int i = 0; i < numArgs - 1; i++) {
			symArgs += "#sym";
		}
		symArgs += ")";

		config.setProperty("symbolic.method", fullMethodName + symArgs);
		config.setProperty("listener", ".symbc.SymbolicListener");
		config.setProperty("symbolic.dp", "cvc3");

		if (boundSearch) {
			config.setProperty("search.depth_limit", "10");
		}

		JPF jpf = new JPF(config);

		try {
			long startTime = System.currentTimeMillis();
			jpf.run();
			long endTime = System.currentTimeMillis();

			writer.append(projectName + ", ");
			writer.append(packageName + ", ");
			writer.append(className + ", ");
			writer.append(methodName + ", ");
			writer.append(String.valueOf(endTime - startTime));
			writer.append(", ");
			writer.append(", ");
			writer.append("\n");
			writer.flush();

		} catch (Exception e) {
			Logger.errorLogger.logln("Pathfinder encountered an error!", 0);
			errorLog.append(projectName + " " + fullClassName + " " + methodName + "\n");
			errorLog.append("Pathfinder error:" + e + "\n\n");
			errorLog.flush();
		}
	}

	public static void runJPFGreen(String fullClassName, String fullMethodName, int numArgs, boolean boundSearch)
			throws IOException {
		String[] jpfArgs = { "-log" };
		Config config = JPF.createConfig(jpfArgs);

		config.setProperty("classpath", "bin");
		config.setProperty("target", fullClassName);
		config.setProperty("search.multiple_errors", "true");

		String symArgs = "(sym";
		for (int i = 0; i < numArgs - 1; i++) {
			symArgs += "#sym";
		}
		symArgs += ")";

		config.setProperty("symbolic.method", fullMethodName + symArgs);

		// green
		config.setProperty("listener", ".symbc.GreenListener");

		config.setProperty("symbolic.green", "true");
		config.setProperty("green.store", "za.ac.sun.cs.green.store.redis.RedisStore");
		config.setProperty("green.services", "sat");
		config.setProperty("green.service.sat", "(slice (canonize cvc3))");
		config.setProperty("green.service.sat.slice", "za.ac.sun.cs.green.service.slicer.SATSlicerService");
		config.setProperty("green.service.sat.canonize", "za.ac.sun.cs.green.service.canonizer.SATCanonizerService");
		config.setProperty("green.service.sat.cvc3", "za.ac.sun.cs.green.service.cvc3.SATCVC3Service");

		if (boundSearch) {
			config.setProperty("search.depth_limit", "10");
		}

		JPF jpf = new JPF(config);

		try {
			long startTime = System.currentTimeMillis();
			jpf.run();
			long endTime = System.currentTimeMillis();

			writerGreen.append(projectName + ", ");
			writerGreen.append(packageName + ", ");
			writerGreen.append(className + ", ");
			writerGreen.append(methodName + ", ");
			writerGreen.append(String.valueOf(endTime - startTime));
			writerGreen.append(", ");
			writerGreen.append(", ");
			writerGreen.append("\n");
			writerGreen.flush();

		} catch (Exception e) {
			Logger.errorLogger.logln("Pathfinder encountered an error!", 0);
			errorLog.append(projectName + " " + fullClassName + " " + methodName + "\n");
			errorLog.append("Pathfinder (Green) error:" + e + "\n\n");
			errorLog.flush();
		}
	}

	public static void main(String[] args) throws IOException {
		
		writer = new FileWriter("without-green.csv");
		writer.append("project, package, class, method, time, constraints, invocations\n");
		writer.flush();

		writerGreen = new FileWriter("with-green.csv");
		writerGreen.append("project, package, class, method, time, constraints, invocations\n");
		writerGreen.flush();
		
		errorLog = new FileWriter("errorLog.txt");
		errorLog.flush();
		
		new FileWriter("CacheHits.txt");
		new FileWriter("Invocations.txt");

		String dirName = "/home/MariaPaquin/project/paclab-transformer.git/benchmarks/";
		File dir = new File(dirName);

		Iterator<File> file_itr = FileUtils.iterateFiles(dir, new String[] { "java" }, true);

		file_itr.forEachRemaining(file -> {
			try {
				run(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		file_itr = FileUtils.iterateFiles(dir, new String[] { "java" }, true);

		file_itr.forEachRemaining(file -> {
			try {
				runGreen(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private static void run(File file) throws IOException {

		String name = file.toString();
		String delims = "/";
		String[] tokens = name.split(delims);
		String projectName = tokens[6];

		try {
			ProgramUnderTest sut = new ProgramUnderTest(file);
			String fullClassName = sut.getFullClassName();

			sut.insertMain();

			Method[] methods = sut.getMethods();
			for (Method method : methods) {

				String fullMethodName = fullClassName + "." + method.getName();

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

				setProjectName(projectName);
				setPackageName(sut.getPackageName());
				setClassName(sut.getClassName());
				setMethodName(method.getName());

				runJPF(fullClassName, fullMethodName, numIntArgs, boundSearch);
			}
		} catch (ClassGenException | ClassNotFoundException e) {
			errorLog.append(file + "\n");
			errorLog.append("Soot error:" + e + "\n\n");
			errorLog.flush();
		} 
	}
	
	private static void runGreen(File file) throws IOException {

		String name = file.toString();
		String delims = "/";
		String[] tokens = name.split(delims);
		String projectName = tokens[6];

		try {
			ProgramUnderTest sut = new ProgramUnderTest(file);
			String fullClassName = sut.getFullClassName();

			sut.insertMain();

			Method[] methods = sut.getMethods();
			for (Method method : methods) {

				String fullMethodName = fullClassName + "." + method.getName();

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

				setProjectName(projectName);
				setPackageName(sut.getPackageName());
				setClassName(sut.getClassName());
				setMethodName(method.getName());

				runJPFGreen(fullClassName, fullMethodName, numIntArgs, boundSearch);

			}
		} catch (ClassGenException e) {
			errorLog.append("Soot error:" + e);
		} catch (ClassNotFoundException e) {
			errorLog.append("Soot error:" + e);
		}
	}

	public static void setProjectName(String name) {
		projectName = name;
	}

	public static void setPackageName(String name) {
		packageName = name;
	}

	public static void setClassName(String name) {
		className = name;
	}

	public static void setMethodName(String name) {
		methodName = name;
	}
}