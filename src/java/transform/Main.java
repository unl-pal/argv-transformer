package transform;


import transform.benchmark.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

import transform.TypeChecking.TypeChecker.CType;
import transform.benchmark.CreateYmlFile;

/**
 * Given a directory of Java projects, this program attempts to transform each
 * .java file in the directory into a compilable benchmark.
 *
 * A directory of benchmarks is created, containing the programs that would
 * successfully compile (before or after transformation) in their original
 * directory structure.
 *
 * @author mariapaquin
 */
public class Main {
	private static PrintWriter printWriter;
	private static File buildDir;
	private static String target = "DEF";

	private final static String DEFAULT_MIN_TYPE_EXPR = "3";
	private final static String DEFAULT_MIN_TYPE_COND = "1";
	private final static String DEFAULT_MIN_TYPE_PARAMS = "0";
	private final static String DEFAULT_TRANSFORM_ALL = "False";
	private final static CType DEFAULT_TYPE = CType.INT;
	
	private static String verifier = "src/java/transform/benchmark/Verifier.java";
	private static boolean debug = false;
	private static boolean transformAll = false;

	 public static String source = "suitablePrgms";
	 public static String dest = "benchmarks";
//	 public static String source = "test/transformer/regression";
//	 public static String dest = "testOutput";

	public static void main(String[] args) throws IOException {
		File tmpDir = Files.createTempDirectory("paclab-transform").toFile();
		buildDir = new File(tmpDir, "bin");



		if (args.length == 2) {
			source = args[0];
			dest = args[1];
		}

		// read the rest of config properties
		File configFile = new File("config.properties");
		int minTypeExpr = Integer.parseInt(DEFAULT_MIN_TYPE_EXPR);
		int minTypeCond = Integer.parseInt(DEFAULT_MIN_TYPE_COND);
		int minTypeParams = Integer.parseInt(DEFAULT_MIN_TYPE_PARAMS);
		transformAll = Boolean.parseBoolean(DEFAULT_TRANSFORM_ALL);
		CType type = DEFAULT_TYPE;
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			target = props.getProperty("target");
			String typeStr = props.getProperty("type", DEFAULT_TYPE.toString());
			switch (typeStr) {
				case "I":
					type = CType.INT;
					break;
				case "R":
					type = CType.REAL;
					break;
				case "B":
					type = CType.BOOLEAN;
					break;
				case "S":
					type = CType.STRING;
					break;
				default:
					type = CType.ANY;
					break;
			}
			minTypeExpr = Integer.parseInt(props.getProperty("minTypeExpr", DEFAULT_MIN_TYPE_EXPR));
			minTypeCond = Integer.parseInt(props.getProperty("minTypeCond", DEFAULT_MIN_TYPE_COND));
			minTypeParams = Integer.parseInt(props.getProperty("minTypeParams", DEFAULT_MIN_TYPE_PARAMS));
			transformAll = Boolean.parseBoolean(props.getProperty("transformAll", DEFAULT_TRANSFORM_ALL));
			debug = Boolean.parseBoolean(props.getProperty("debug"));
			//verifier = props.getProperty(verifier).toString();
		} catch (IOException exp) {
			System.out.println("Invalid configuration file.");
			System.exit(1);
		}

		System.out.println(type + " " + minTypeExpr + " " + minTypeCond + " " + minTypeParams);

		File srcDir = new File(source);
		File destDir = new File(dest);

		printWriter = new PrintWriter(System.out, true);

		if (destDir.exists()) {
			FileUtils.forceDelete(destDir);
		}
		FileUtils.forceMkdir(destDir);

		try {
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (buildDir.exists()) {
			try {
				FileUtils.forceDelete(buildDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileUtils.forceMkdir(buildDir);

		ArrayList<File> successfulCompiles = new ArrayList<File>();
		ArrayList<File> unsuccessfulCompiles = new ArrayList<File>();
		Iterator<File> file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

		file_itr.forEachRemaining (file -> {
			boolean success = compile(file);
			if (!success || transformAll) {
				unsuccessfulCompiles.add(file);
			} 
		  if (success) {
				successfulCompiles.add(file);
			}
		});

		// TODO should all the different outputs alsways be printed or a part of debug or other?

		System.out.println("================================================");
		System.out.println("Before Transformation:");
		System.out.println("================================================");

		int failedCompilation = (transformAll) ? unsuccessfulCompiles.size() - successfulCompiles.size():unsuccessfulCompiles.size();
		System.out.println("================ FAILURES ================");
		System.out.println("Number of unsuccessful intial compilation " + failedCompilation);
		for (File file : unsuccessfulCompiles) {
			System.out.println(file.toString());
			}

		System.out.println("================ SUCCESS =================");
		System.out.println("Number of successful intial compilation " + successfulCompiles.size());
		for (File file : successfulCompiles) {
			System.out.println(file.toString());
			}
    
		//System.out.println(unsuccessfulCompiles + " ------- " + successfulCompiles);

		Transformer transformer = new Transformer(unsuccessfulCompiles, target);
		transformer.transformFiles(minTypeExpr, minTypeCond, minTypeParams, type);

		Transformer annotate = new Transformer(successfulCompiles, target);
		annotate.annotateFiles();

		// Those that are successfully compiles on the first try just add filtered

		file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

    
		// also delete those files where all methods after transformations
		// were not be able to meet the selection criteria.
		// do not remove uncompiled files if target is SVCOMP as for SVCOMP one
		// dependency will not be compileable

		file_itr.forEachRemaining(file -> {
			boolean success = compile(file);
			// Do Not delete failed to compile benchmarks if debugging
			// With changes to compile() SVCOMP Benchmarks can be compiled
			if (!success && !debug) {
				try {
					Files.delete(file.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (success) {
				if (!successfulCompiles.contains(file)) successfulCompiles.add(file);
				unsuccessfulCompiles.remove(file);
			}

			// Create the YAML if targeting SVCOMP
			if(target.equals("SVCOMP")) {
				prepareForSvcompBenchmark(file);
			}
		});

		// Catch any benchmarks that can no longer be compiled after transformation
		ArrayList<File> newFails = new ArrayList<File>();
		for (File file : successfulCompiles) {
			if (unsuccessfulCompiles.contains(file)) {
				newFails.add(file);
			}
		}

		// Fix later so these ^V can be combined, unsafe as is
		for (File file : newFails) {
			successfulCompiles.remove(file);
		}
    
		System.out.println("================================================");
		System.out.println("After Transformation:");
		System.out.println("================ FAILURES ================");
		System.out.println("Number of unsuccessful compilations " + unsuccessfulCompiles.size());
		for (File file : unsuccessfulCompiles) {
			System.out.println(file.toString());
			}

		System.out.println("================ SUCCESS =================");
		System.out.println("Number of successful compilations " + successfulCompiles.size());
		for (File file : successfulCompiles) {
			System.out.println(file.toString());
			}
		System.out.println("================ No More =======================");
		System.out.println("No longer compiles after Transform " + newFails.size());
		for (File file : newFails) {
			System.out.println(file.toString());
		}

		try {
			FileUtils.forceDelete(tmpDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		removeEmptyDirs(destDir);

		if (successfulCompiles.size() == 0)
			System.exit(-1);
	}

	/**
	 * Takes a file and attemps to compile using the file and verifier
	 * @param file - suitablePrgms file to attempt compilation
	 * @return completion status of the attempted compilation
	 */
	private static boolean compile(File file) {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null)
			throw new RuntimeException("Could not get javac - are you running with a JDK or a JRE?");


		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
		int runErrors = compiler.run(null, outputStream, errorStream, "-g", "-d", buildDir.getAbsolutePath(), "-cp",
				System.getProperty("java.class.path"), file.toString(), verifier);
		// System.out.println("Num compilation erros in " + file.getParent() + " are " +
		// runErrors);
		return runErrors == 0;
	}

	private static void removeEmptyDirs(File file) {
		File[] files = file.listFiles();
		if (files.length > 0) {
			for (File f : files) {
				if (f.isDirectory()) {
					removeEmptyDirs(f);
				}
			}
		}
		if (file.listFiles().length == 0)
			try {
				FileUtils.forceDelete(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}


	/**
	 * Creates YML for matching benchmark for SVCOMP benchmarks
	 * @param file - svcomp compatible benchmark file
	 */
	private static void prepareForSvcompBenchmark(File file) {
		// Path to Save YML file
		File parentDirectory = new File(file.getParent());

		// Name of YML file
		String fileNameWithExtension = file.getName();
		String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));

		File newFilePath = new File(parentDirectory.getPath() + "/" + fileNameWithoutExtension);
		if (newFilePath.exists()) {
			try {
				FileUtils.forceDelete(newFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileUtils.forceMkdir(newFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		CreateYmlFile.buildFile(file.getParent(), fileNameWithoutExtension, file.getParentFile().getName(), true);

	}
}
