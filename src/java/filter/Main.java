package filter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import filter.file.FileFilter;
import logging.Logger;

/**
 * Filter a directory full of repositories, one repository at a time.
 *
 * input: database, a directory of repositories
 * output: suitablePrgms, a directory containing only the java files
 * suitable for symbolic execution found in the database directory
 */
public class Main {
  private static final Logger logger = Logger.defaultLogger;

  private static final String DEFAULT_MIN_EXPR = "0";
  private static final String DEFAULT_MIN_IFSTMT = "0";
  private static final String DEFAULT_MIN_PARAMS = "0";
  private static final String DEFAULT_TYPE = "S";

  public static void main(String[] args) throws IOException {

    Logger logger = Logger.defaultLogger.enterContext("Main");
    int totalMethods = 0;

    String inputPath = "database";
    // String inputPath = "src/test/strings";
    String outputPath = "suitableStrPrgms";

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
    for (File repo : srcRoot.listFiles(File::isDirectory)) {

      logger.logln("Processing repository: " + repo.getName(), 1);

      File tempRepo = new File(tmpRoot, repo.getName());

      try {
        // 1. Copy a single repository
        FileUtils.copyDirectory(repo, tempRepo);

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
          allInfo.append(repo);
          allInfo.append("\n");
          allInfo.append(filter.getSummaryInfo());
          allInfo.append("\n");
        }

        // 3. Copy suitable files to destination
        for (File f : suitable) {
          String relPath = f.getAbsolutePath().replace(tempRepo.getAbsolutePath(), "");

          File destinationFile = new File(destRoot, repo.getName() + File.separator + relPath);
          destinationFile.getParentFile().mkdirs();

          if (destinationFile.exists()) {
            destinationFile.delete();
          }

          FileUtils.copyFile(f, destinationFile);

          // create 'metadata' info file for each suitable file
          String info = filter.getFileInfo(f);
          String infoFileName = relPath.replace(File.separator, ".") + ".info";

          File infoFile = new File(destRoot, repo.getName() + File.separator + infoFileName);
          FileUtils.writeStringToFile(infoFile, info, "UTF-8");

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
