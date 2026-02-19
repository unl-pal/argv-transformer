package filter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.yaml.snakeyaml.Yaml;

import filter.file.FileFilter;
import logging.Logger;
import sourceAnalysis.AnalyzedFile;

/**
 * Filter a directory full of repositories, one repository at a time.
 *
 * input: database, a directory of repositories
 * output: suitablePrgms, a directory containing only the java files
 * suitable for symbolic execution found in the database directory
 */
public class Main {
  private static final Logger logger = Logger.defaultLogger.enterContext("filter.Main");

  private static final String DEFAULT_MIN_EXPR = "0";
  private static final String DEFAULT_MIN_IFSTMT = "0";
  private static final String DEFAULT_MIN_PARAMS = "0";
  private static final String DEFAULT_TYPE = "S";

  // private static String inputPath = "database";
  // private static String outputPath = "suitableStrPrgms";
  public static String resourcesPath = "/home/nat/Repos/resources-argv/";
  public static String programPath = resourcesPath + "java-programs/";
  public static String filteredPath = resourcesPath + "filtered-programs/";
  public static String inputPath = programPath + "java-repos";
  public static String outputPath = filteredPath + "suitableJavaRepos";
  // private static String inputPath = "java-repos";
  // private static String outputPath = "suitableJavaRepos";
  // private static String inputPath = "old-java-programs";
  // private static String outputPath = "suitableOldJava";

  public static void main(String[] args) throws IOException {

    int totalMethods = 0;

    if (args.length == 2) {
      inputPath = args[0];
      outputPath = args[1];
    }

    /* ---------------- Load configuration ---------------- */

    File configFile = new File("config.properties");
    int minExpr;
    int minIfStmt;
    int minParams;
    String type;
    int debugLevel;

    try (FileReader reader = new FileReader(configFile)) {
      Properties props = new Properties();
      props.load(reader);

      type = props.getProperty("type", DEFAULT_TYPE);
      minExpr = Integer.parseInt(props.getProperty("minExpr", DEFAULT_MIN_EXPR));
      minIfStmt = Integer.parseInt(props.getProperty("minIfStmt", DEFAULT_MIN_IFSTMT));
      minParams = Integer.parseInt(props.getProperty("minParams", DEFAULT_MIN_PARAMS));
      debugLevel = Integer.parseInt(props.getProperty("debugLevel", "0"));

    } catch (IOException e) {
      System.err.println("Invalid configuration file.");
      System.err.println(e.getMessage());
      return;
    }

    logger.setDebugLevel(debugLevel);
    logger.logln("Running Filter:\n\ttype: " + type + ", minExpr: " + minExpr + ", cond: " + minIfStmt
        + ", params: " + minParams, 0);

    /* ---------------- Prepare directories ---------------- */

    File srcRoot = new File(inputPath);
    File destRoot = new File(outputPath);

    if (!srcRoot.isDirectory()) {
      throw new IllegalArgumentException("Input path is not a directory: " + inputPath);
    }

    if (destRoot.exists()) {
      FileUtils.forceDelete(destRoot);
    }
    FileUtils.forceMkdir(destRoot);

    File tmpRoot = Files.createTempDirectory("paclab-filter").toFile();

    /* ---------------- Process repositories sequentially ---------------- */

    StringBuilder allInfo = new StringBuilder();
    allInfo.append("repo,file,suitable_methods,type_ops\n");

    for (File repo : srcRoot.listFiles(File::isDirectory)) {

      logger.logln("Processing repository: " + repo.getName(), 1);

      File tempRepo = new File(tmpRoot, repo.getName());

      try {
        // 1. Copy a single repository
        IOFileFilter javaFileFilter = FileFilterUtils.suffixFileFilter(".java");
        IOFileFilter directoryFilter = FileFilterUtils.directoryFileFilter();
       java.io.FileFilter copyFilter = FileFilterUtils.or(javaFileFilter, directoryFilter);

        FileUtils.copyDirectory(repo, tempRepo, copyFilter);

        // 2. Filter this repository only
        FileFilter filter = new FileFilter(
            tempRepo,
            type,
            minExpr,
            minIfStmt,
            minParams);

        filter.collectJavaFiles();
        filter.collectSuitableFiles();

        ArrayList<File> suitable = filter.getSuitableFiles();
        if (!suitable.isEmpty()) {
          allInfo.append(filter.getSummaryInfo(repo.getName()));
        }

        // 3. Copy suitable files to destination
        for (File f : suitable) {
          String relPath = f.getAbsolutePath().replace(tempRepo.getAbsolutePath(), "");
          String parents = relPath.replace(f.getName(), "").replace(File.separator, ".").substring(1); // flatten
                                                                                                       // directory
                                                                                                       // structure
          parents = !parents.isEmpty() ? parents.substring(0, parents.length() - 1) : parents; // remove trailing '.'

          File destinationFile = new File(destRoot,
              repo.getName() + File.separator + parents + File.separator + f.getName());
          destinationFile.getParentFile().mkdirs();

          if (destinationFile.exists()) {
            destinationFile.delete();
          }

          FileUtils.copyFile(f, destinationFile);

          // create 'metadata' info file for each suitable file
          // String info = filter.getFileInfo(f);
          String infoFileName = relPath.replace(File.separator, ".") + ".yaml";

          AnalyzedFile af = filter.getAnalyzedFile(f);
          File infoFile = new File(destRoot, repo.getName() + File.separator + infoFileName);
          try (FileWriter writer = new FileWriter(infoFile)) {
            Yaml yaml = new Yaml();
            yaml.dump(af, writer);
          }
          // FileUtils.writeStringToFile(infoFile, info, "UTF-8");

        }

        if (!suitable.isEmpty()) {
          logger.logln("  Copied " + suitable.size() + " suitable files to " + destRoot.getAbsolutePath(), 1);
        }
        totalMethods += suitable.size();

      } catch (Exception e) {
        System.err.println("Error processing " + repo.getName());
        e.printStackTrace();
      } finally {
        // 4. Cleanup temp directory for this repo
        if (tempRepo.exists()) {
          FileUtils.forceDelete(tempRepo);
        }
      }
    }

    FileUtils.writeStringToFile(new File(destRoot, "summary.info"), allInfo.toString(), "UTF-8");

    // Cleanup root temp directory
    FileUtils.forceDelete(tmpRoot);

    logger.logln("Filtering Complete, total methods/files: " + totalMethods, 0);
  }
}
