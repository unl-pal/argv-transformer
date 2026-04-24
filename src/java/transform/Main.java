package transform;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
	private static File buildDir;
	private static String target = "DEF";

	private final static String DEFAULT_MIN_TYPE_EXPR = "3";
	private final static String DEFAULT_MIN_TYPE_COND = "1";
	private final static String DEFAULT_MIN_TYPE_PARAMS = "0";
	private final static String DEFAULT_TRANSFORM_ALL = "False";
	private final static CType DEFAULT_TYPE = CType.INT;
	
	private static String verifier = "";
	private static boolean debug = false;
	private static boolean transformAll = false;

	public static String source = "suitablePrgms";
	public static String dest = "benchmarks";
//	 public static String source = "src/test/transformer/integration";
//	 public static String source = "testsFromReport";
//	 public static String dest = "testOutput";

	 public static void main(String[] args) throws IOException {
	     File tmpDir = Files.createTempDirectory("paclab-transform").toFile();
	     buildDir = new File(tmpDir, "bin");

	     if (args.length == 2) {
	         source = args[0];
	         dest = args[1];
	     }

	     // Load config
	     File configFile = new File("config.properties");
	     int minTypeExpr = Integer.parseInt(DEFAULT_MIN_TYPE_EXPR);
	     int minTypeCond = Integer.parseInt(DEFAULT_MIN_TYPE_COND);
	     int minTypeParams = Integer.parseInt(DEFAULT_MIN_TYPE_PARAMS);
	     transformAll = Boolean.parseBoolean(DEFAULT_TRANSFORM_ALL);
	     CType type = DEFAULT_TYPE;

	     try (FileReader reader = new FileReader(configFile)) {
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
	         verifier = props.getProperty("verifier");
	     } catch (IOException e) {
	         System.out.println("Invalid configuration file.");
	         System.exit(1);
	     }

	     System.out.println(type + " " + minTypeExpr + " " + minTypeCond + " " + minTypeParams);

	     File sourceFile = new File(source);
	     if (!sourceFile.isDirectory()) {
		     // If source is a file, make a temp directory to hold it
		     File tempDir = Files.createTempDirectory("paclab-transform").toFile();
		     FileUtils.copyFileToDirectory(sourceFile, tempDir);
		     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		    	 try {
                    FileUtils.forceDelete(tempDir);
                } catch (FileNotFoundException e) {
                    // do nothing; temp dir has already been deleted
                } catch (IOException e) {
					System.err.println("Failed to delete temp directory");
				}
		     }));
		     sourceFile = tempDir;
	     }
	     File srcDir = sourceFile;
	     File destDir = new File(dest);

	     if (destDir.exists()) FileUtils.forceDelete(destDir);
	     FileUtils.forceMkdir(destDir);

	     if (buildDir.exists()) FileUtils.forceDelete(buildDir);
	     FileUtils.forceMkdir(buildDir);

	     // For reporting only
	     List<File> successful = new ArrayList<>();
	     List<File> failed = new ArrayList<>();
	     List<File> nowFails = new ArrayList<>();

	     // iterate source files and process one by one
	     Iterator<File> srcFiles = FileUtils.iterateFiles(srcDir, new String[]{"java"}, true);
	     
	     final int fMinTypeExpr = minTypeExpr;
	     final int fMinTypeCond = minTypeCond;
	     final int fMinTypeParams = minTypeParams;
	     final CType fType = type;

	     srcFiles.forEachRemaining(srcFile -> {
	         try {
	             // create matching destination path
	             Path relative = srcDir.toPath().relativize(srcFile.toPath());
	             File destFile = new File(destDir, relative.toString());
	             destFile.getParentFile().mkdirs();

	             // ==== COPY ====
	             Files.copy(srcFile.toPath(), destFile.toPath());

	             // ==== INITIAL COMPILE ====
	             boolean compilesInitially = compile(destFile);

	             if (!compilesInitially && !transformAll) {
	                 failed.add(destFile);
	             }

	             // ==== TRANSFORM (if failed initially OR transformAll) ====
	             if (!compilesInitially || transformAll) {
	                 try {
	                     Transformer transformer = new Transformer(new ArrayList<File>(Collections.singletonList(destFile)), target);
	                     transformer.transformFiles(fMinTypeExpr, fMinTypeCond, fMinTypeParams, fType);
	                 } catch (Exception ex) {
	                     System.err.println("Transform error: " + destFile + " -> " + ex.getMessage());
	                 }
	             }

	             // ==== ANNOTATE successful initial compiles ====
	             if (compilesInitially) {
	                 try {
	                     Transformer annotator = new Transformer(new ArrayList<File>(Collections.singletonList(destFile)), target);
	                     annotator.annotateFiles();
	                 } catch (Exception ex) {
	                     System.err.println("Annotation error: " + destFile + " -> " + ex.getMessage());
	                 }
	             }

	             // ==== RECOMPILE AFTER TRANSFORMS ====
	             boolean compilesAfter = destFile.exists() && compile(destFile);

	             if (!compilesAfter && !debug) {
	                 Files.deleteIfExists(destFile.toPath());
	                 failed.add(destFile);
	                 if (compilesInitially) nowFails.add(destFile);
	             } else if (compilesAfter) {
	                 successful.add(destFile);
	                 failed.remove(destFile);

	                 if ("SVCOMP".equals(target)) {
	                     createSVCompYmlFile(destFile);
	                     restructureForSVCompFormat(destFile.toPath());
	                 }
	             }

	         } catch (Exception e) {
	             System.err.println("Exception processing " + srcFile + ": " + e.getMessage());
	             e.printStackTrace();
	         }
	     });

	     // ===== OUTPUT RESULTS =====
	     System.out.println("================================================");
	     System.out.println("Before Transformation:");
	     System.out.println("Initial failures: (transformAll allows rescue)");

	     if (failed.isEmpty()) {
		     System.out.println("NO FAILURES");
	     } else {
		     System.out.println(failed.size() + " failures");
	     }
	     for (File f : failed) System.out.println(f);

	     System.out.println("================ SUCCESS AFTER FULL PIPELINE ================");
	     if (successful.isEmpty()) {
		     System.out.println("NO SUCCESS");
	     } else {
		     System.out.println(successful.size() + " successful");
	     }
	     for (File f : successful) System.out.println(f);

	     System.out.println("================ NOW FAIL AFTER TRANSFORM ===================");
	     if (nowFails.isEmpty()) {
		     System.out.println("NO FAILS POST TRANSFORM");
	     } else {
		     System.out.println(nowFails.size() + " now fails");
	     }
	     for (File f : nowFails) System.out.println(f);

	     // cleanup temp build
	     FileUtils.forceDelete(tmpDir);

//	     // SVCOMP restructure
//	     if ("SVCOMP".equals(target)) {
//	         List<Path> javaFiles = Files.walk(Paths.get(dest))
//	                 .filter(path -> path.toString().endsWith(".java"))
//	                 .collect(Collectors.toList());
//	         javaFiles.forEach(Main::restructureForSVCompFormat);
//	     }

	     removeEmptyDirs(destDir);

	     if (successful.isEmpty()) System.exit(-1);
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
	private static void createSVCompYmlFile(File file) {
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

		CreateYmlFile.buildFile(file.getParent(), fileNameWithoutExtension, true, true);

	}
	
	private static void restructureForSVCompFormat(Path javaFilePath) {
        try {
            String fileNameWithoutExt = javaFilePath.getFileName().toString().replace(".java", "");
            String content = readFile(javaFilePath);
            String newContent = Transformer.updateClassName(content);
            newContent = Transformer.renameInstanceVariables(newContent, fileNameWithoutExt);
            
            Path parentDir = javaFilePath.getParent();
            Path newDir = parentDir.resolve(fileNameWithoutExt);
            Files.createDirectories(newDir);

            Path newFilePath = newDir.resolve("Main.java");
            writeFile(newFilePath, newContent);

            Files.delete(javaFilePath);
            System.out.println("Moved and renamed: " + javaFilePath + " -> " + newFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(Path path) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    private static void writeFile(Path path, String content) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
            writer.close();
        }
    }
}
