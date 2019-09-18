package transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

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
 *
 */
public class Main {
	private static PrintWriter printWriter;

	public static void main(String[] args) throws IOException {		
		printWriter = new PrintWriter(System.out, true);
		
		String source = "suitablePrgms";
		File srcDir = new File(source);

		String dest = "benchmarks";
		File destDir = new File(dest);

		if (destDir.exists()) {
			FileUtils.forceDelete(destDir);
		}
		FileUtils.forceMkdir(destDir);

		try {
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<File> unsuccessfulCompiles = new ArrayList<File>();
		
		File buildDir = new File("build");

		if (buildDir.exists()) {
			try {
				FileUtils.forceDelete(buildDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		FileUtils.forceMkdir(buildDir);

		Iterator<File> file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

		file_itr.forEachRemaining(file -> {
			boolean success = compile(file);
			if (!success) {
				unsuccessfulCompiles.add(file);
			}
		});

		Transformer transformer = new Transformer(unsuccessfulCompiles);
		transformer.transformFiles();

		file_itr = FileUtils.iterateFiles(destDir, new String[] { "java" }, true);

		file_itr.forEachRemaining(file -> {
			boolean success = compile(file);
			if (!success) {
				try {
					Files.delete(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private static boolean compile(File file) {
		
		String command = "javac -g -d bin/ -cp .:/home/MariaPaquin/pathfinder/jpf-symbc/build/classes " + file;

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
}
