package transform;

import java.io.BufferedReader;
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

	public static void main(String[] args) throws IOException {
		File tmpDir = Files.createTempDirectory("paclab-transform").toFile();
		buildDir = new File(tmpDir, "bin");

		String source = "suitablePrgms/moloneycharles";
		String dest = "testOutput";

		if (args.length == 2) {
			source = args[0];
			dest = args[1];
		}

		//read the rest of config properties
		File configFile = new File("config.properties");
		int minTypeExpr = Integer.parseInt(DEFAULT_MIN_TYPE_EXPR);
		int minTypeCond = Integer.parseInt(DEFAULT_MIN_TYPE_COND);
		int minTypeParams = Integer.parseInt(DEFAULT_MIN_TYPE_PARAMS);
		boolean transformAll = Boolean.parseBoolean(DEFAULT_TRANSFORM_ALL);
		CType type = DEFAULT_TYPE;
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			target = props.getProperty("target");
			String typeStr = props.getProperty("type", DEFAULT_TYPE.toString());
			switch(typeStr) {
			case "I": type = CType.INT; break;
			case "R" : type = CType.REAL; break;
			case "B" : type = CType.BOOLEAN; break;
			case "S" : type = CType.STRING; break;
			default : type = CType.ANY; break;
			}
			minTypeExpr = Integer.parseInt(props.getProperty("minTypeExpr", DEFAULT_MIN_TYPE_EXPR));
			minTypeCond = Integer.parseInt(props.getProperty("minTypeCond", DEFAULT_MIN_TYPE_COND));
			minTypeParams = Integer.parseInt(props.getProperty("minTypeParams", DEFAULT_MIN_TYPE_PARAMS));
			transformAll = Boolean.parseBoolean(props.getProperty("transformAll", DEFAULT_TRANSFORM_ALL));
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

		if (transformAll) {
			file_itr.forEachRemaining(file -> unsuccessfulCompiles.add(file));
		} else {
			file_itr.forEachRemaining(file -> {
				boolean success = compile(file);
				if (!success) {
					unsuccessfulCompiles.add(file);
				} else {
					successfulCompiles.add(file);
				}
			});
		}
		
		System.out.println(unsuccessfulCompiles + " ------- " + successfulCompiles);

		Transformer transformer = new Transformer(unsuccessfulCompiles, target);
		transformer.transformFiles(minTypeExpr, minTypeCond, minTypeParams, type);
		
		Transformer annotate = new Transformer(successfulCompiles, target);
		annotate.annotateFiles();
		
		//Those that are successfully compiles on the first try just add filtered

		file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

		//also delete those files where all methods after transformations 
		//were not be able to meet the selection criteria.
		file_itr.forEachRemaining(file -> {
			boolean success = compile(file);
			if (!success) {
				try {
					Files.delete(file.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				//System.out.println("compiled " + file.getName());
				successfulCompiles.add(file);
				//unsuccessfulCompiles.remove(file);
			}
		});

		//System.out.println(unsuccessfulCompiles.size() + " +++++ " + successfulCompiles.size());
		
		try {
			FileUtils.forceDelete(tmpDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		removeEmptyDirs(destDir);

		if (successfulCompiles.size() == 0)
			System.exit(-1);
	}

	private static boolean compile(File file) {
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null)
			throw new RuntimeException("Could not get javac - are you running with a JDK or a JRE?");

		return compiler.run(null, null, null, "-g", "-d", buildDir.getAbsolutePath(), "-cp",
				System.getProperty("java.class.path"), file.toString()) == 0;
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
}
