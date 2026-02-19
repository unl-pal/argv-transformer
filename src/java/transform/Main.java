package transform;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import logging.Logger;
import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

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

  private static String verifier = "";
  private static int debug = 0;
  private static boolean transformAll = false;

  public static final Logger logger = Logger.defaultLogger.enterContext("transform.Main");
  public static final Logger errorLogger = Logger.defaultLogger.enterContext("transform.Main");

  // public static String source = "database";
  // public static String dest = "benchmarks";
  // public static String source = "src/test/transformer/integration";
  // public static String source = "testsFromReport";
  // public static String dest = "testOutput";
//  public static String source = "src/test/strings/output/expected/filter";
//  public static String dest = "src/test/strings/output/expected/transform";
//   public static String source = "naughtyStringPrograms";
//   public static String dest = "naughtyStringBenchmarks";
  // public static String source = "suitableStrPrgms";
  // public static String dest = "strBenchmarks";
  public static String resourcesPath = "/home/nat/Repos/resources-argv/";
  public static String filteredPath = resourcesPath + "filtered-programs/";
  public static String benchmarkPath = resourcesPath + "transformed-programs/";
  public static String source = filteredPath + "suitableJavaRepos";
  public static String dest = benchmarkPath + "javaStringBenches";
  // public static String source = "suitableOldJava";
  // public static String dest = "oldJavaBenches";
  public static Set<String> successfulNames = new HashSet<>();

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
      debug = Integer.parseInt(props.getProperty("debugLevel"));
      logger.setDebugLevel(debug);
      errorLogger.setDebugLevel(debug);
      verifier = props.getProperty("verifier");
    } catch (IOException e) {
      errorLogger.logln("Working dir: " + System.getProperty("user.dir"), 1);
      errorLogger.logln("Invalid configuration file.", 1);
      e.printStackTrace();
      System.exit(1);
    }
    logger.logln("Targeting " + type + " methods with at least " + minTypeExpr + " expressions, " + minTypeCond + " conditionals, and " + minTypeParams + " parameters", 1);

    File srcDir = new File(source);
    File destDir = new File(dest);
    printWriter = new PrintWriter(System.out, true);

    if (destDir.exists())
      FileUtils.forceDelete(destDir);
    FileUtils.forceMkdir(destDir);

    if (buildDir.exists())
      FileUtils.forceDelete(buildDir);
    FileUtils.forceMkdir(buildDir);

    // For reporting only
    List<File> successful = new ArrayList<>();
    List<File> failed = new ArrayList<>();
    List<File> nowFails = new ArrayList<>();

    final int fMinTypeExpr = minTypeExpr;
    final int fMinTypeCond = minTypeCond;
    final int fMinTypeParams = minTypeParams;
    final CType fType = type;

    // iterate source files and process one by one
    Iterator<File> srcFiles = FileUtils.iterateFiles(srcDir, new String[] { "java" }, true);
    // make order deterministic
    List<File> files = new ArrayList<>();
    srcFiles.forEachRemaining(files::add);
    files.sort(Comparator.comparing(f -> srcDir.toPath().relativize(f.toPath()).toString()));

    for (File srcFile : files) {
      try {
        // create matching destination path
        Path relative = srcDir.toPath().relativize(srcFile.toPath());
        File destFile = new File(destDir, relative.toString());
        destFile.getParentFile().mkdirs();

        // ==== COPY ====
        Files.copy(srcFile.toPath(), destFile.toPath());

        logger.logln("Processing: " + srcFile, 2);
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
            errorLogger.logln("Transform error: " + destFile + " -> " + ex.getMessage(),1);
          }
        }

        // ==== ANNOTATE successful initial compiles ====
        if (compilesInitially && destFile.exists()) {
          try {
            Transformer annotator = new Transformer(new ArrayList<File>(Collections.singletonList(destFile)), target);
            annotator.annotateFiles();
          } catch (Exception ex) {
            errorLogger.logln("Annotation error: " + destFile + " -> " + ex.getMessage(), 1);
          }
        }

        // ==== RECOMPILE AFTER TRANSFORMS ====
        boolean compilesAfter = compile(destFile); // won't compile if file deleted during transform

        if (!compilesAfter) {
          Files.deleteIfExists(destFile.toPath());
          failed.add(destFile);
          if (compilesInitially)
            nowFails.add(destFile);
        } else if (compilesAfter) {
          successful.add(destFile);
          failed.remove(destFile);

          // getInfoFile(srcFile, destFile);
          if ("SVCOMP".equals(target)) {
            createSVCompYmlFile(destFile);
            // rename class etc. and move
            logger.logln("Restructuring for SVCOMP format: " + destFile, 1);
            restructureForSVCompFormat(destFile.toPath());
          }
        }

      } catch (Exception e) {
        errorLogger.logln("Exception processing " + srcFile + ": " + e.getMessage(), 1);
        e.printStackTrace();
      }
    }

    // ===== OUTPUT RESULTS =====
    logger.logln("================================================", 2);
    logger.logln("Before Transformation:", 1);
    logger.logln("Initial failures: (transformAll allows rescue)", 2);

    if (failed.isEmpty()) {
      logger.logln("NO FAILURES", 2);
    } else {
      logger.logln(failed.size() + " failures", 2);
    }
    for (File f : failed)
      logger.logln(f.getName() + " at " + f.getPath(), 2);

    logger.logln("================ SUCCESS AFTER FULL PIPELINE ================", 2);
    if (successful.isEmpty()) {
      logger.logln("NO SUCCESS", 2);
    } else {
      logger.logln(successful.size() + " successful", 2);
    }
    for (File f : successful)
      logger.logln(f.getName() + " at " + f.getPath(), 2);

    logger.logln("================ NOW FAIL AFTER TRANSFORM ===================", 2);
    if (nowFails.isEmpty()) {
      logger.logln("NO FAILS POST TRANSFORM", 2);
    } else {
      logger.logln(nowFails.size() + " now fails", 2);
    }
    for (File f : nowFails)
      logger.logln(f.getName() + " at " + f.getPath(), 2);

    // cleanup temp build
    FileUtils.forceDelete(tmpDir);

    // // SVCOMP restructure
    // if ("SVCOMP".equals(target)) {
    // List<Path> javaFiles = Files.walk(Paths.get(dest))
    // .filter(path -> path.toString().endsWith(".java"))
    // .collect(Collectors.toList());
    // javaFiles.forEach(Main::restructureForSVCompFormat);
    // }

    removeEmptyDirs(destDir);

    if (successful.isEmpty())
      logger.logln("No programs transformed successfully.", 1);
  }

  private static void getInfoFile(File srcFile, File destFile) {
    File parent = srcFile.getParentFile();
    String repo = parent.getParent();
    File infoFile = new File(repo, "." + parent.getName() + "." + destFile.getName() + ".yaml");
    try {
      FileUtils.copyFileToDirectory(infoFile, destFile.getParentFile());
    } catch (IOException e) {
      errorLogger.logln("Info file copy error: " + destFile + " -> " + e.getMessage(), 1);
    }
  }

  /**
   * Takes a file and attemps to compile using the file and verifier
   * 
   * @param file - suitablePrgms file to attempt compilation
   * @return completion status of the attempted compilation
   */
  private static boolean compile(File file) {
    if (!file.exists())
      return false; // discarded unsuitables program

    final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    if (compiler == null)
      throw new RuntimeException("Could not get javac - are you running with a JDK or a JRE?");

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    int runErrors = compiler.run(null, outputStream, errorStream, "-g", "-d", buildDir.getAbsolutePath(), "-cp",
        System.getProperty("java.class.path"), file.toString(), verifier);
    // logger.logln("Num compilation erros in " + file.getParent() + " are " +
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
   * 
   * @param file - svcomp compatible benchmark file
   */
  private static void createSVCompYmlFile(File file) {
    // Path to Save YML file
    File parentDirectory = new File(file.getParent());
    String repo = parentDirectory.getName();

    // Name of YML file
    String fileNameWithExtension = file.getName();
    String fileNameWithoutExtension = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));

    // TODO: add exception handling
    File newFilePath = new File(parentDirectory.getPath().replace("/" + repo,"." + repo) + "/" + fileNameWithoutExtension);
//    if (newFilePath.exists()) {
      CreateYmlFile.buildFile(file.getParent(), fileNameWithoutExtension, true, true);
//    }

  }

  private static void restructureForSVCompFormat(Path javaFilePath) {
    try {
      String fileNameWithoutExt = javaFilePath.getFileName().toString().replace(".java", "");

      String content = readFile(javaFilePath);
      String newContent = Transformer.updateClassName(content);
      newContent = Transformer.renameInstanceVariables(newContent, fileNameWithoutExt);

      Path parentDir = javaFilePath.getParent();
      // while (!successfulNames.add(fileNameWithoutExt)){
        // conflicts will arise during SV-COMP runs

      // }
      Path newDir = parentDir.resolve(fileNameWithoutExt);
      Files.createDirectories(newDir);

      Path newFilePath = newDir.resolve("Main.java");
      writeFile(newFilePath, newContent);

      Files.delete(javaFilePath);
      logger.logln("Moved and renamed: " + javaFilePath + " -> " + newFilePath, 1);

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
