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

	public static void main(String[] args) throws IOException {
		File tmpDir = Files.createTempDirectory("paclab-transform").toFile();
		buildDir = new File(tmpDir, "bin");

		String source = "suitablePrgms";
		String dest = "benchmarks";

		if (args.length == 2) {
			source = args[0];
			dest = args[1];
		}
		
		//read the rest of parameters from config.properties
		File configFile = new File("config.properties");
		try {
			FileReader reader = new FileReader(configFile);
			Properties props = new Properties();
			props.load(reader);
			target = props.getProperty("target");
		} catch (IOException exp) {
			System.out.println("Invalid configuration file.");
			System.exit(1);
		}
		

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

		file_itr.forEachRemaining(file -> {
			boolean success = compile(file);
			if (!success) {
				unsuccessfulCompiles.add(file);
			} else {
				successfulCompiles.add(file);
			}
		});
		
		System.out.println(unsuccessfulCompiles + " ------- " + successfulCompiles);

		Transformer transformer = new Transformer(unsuccessfulCompiles, target);
		transformer.transformFiles();
		
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

	private static boolean compile2(File file) {
		String command = "javac -g -d " + buildDir.getAbsolutePath()
				+ " -cp .:/home/MariaPaquin/pathfinder/jpf-symbc/build/classes " + file;

		boolean success = false;
		try {
			Process pro = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", command });
			pro.waitFor();
			printCompileExitStatus(command + " stdout:", pro.getInputStream());
			printCompileExitStatus(command + " stderr:", pro.getErrorStream());
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
