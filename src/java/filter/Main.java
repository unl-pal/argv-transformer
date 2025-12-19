package filter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import filter.file.FileFilter;

/**
 * Filter a directory full of repositories, one repository at a time.
 *
 * input: database, a directory of repositories
 * output: suitablePrgms, a directory containing only the java files
 * suitable for symbolic execution found in the database directory
 */
public class Main {

    private static final String DEFAULT_MIN_EXPR = "0";
    private static final String DEFAULT_MIN_IFSTMT = "0";
    private static final String DEFAULT_MIN_PARAMS = "0";
    private static final String DEFAULT_TYPE = "I";

    public static void main(String[] args) throws IOException {

        String inputPath = "database";
        String outputPath = "suitablePrgms";

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

        try (FileReader reader = new FileReader(configFile)) {
            Properties props = new Properties();
            props.load(reader);

            type = props.getProperty("type", DEFAULT_TYPE);
            minExpr = Integer.parseInt(props.getProperty("minExpr", DEFAULT_MIN_EXPR));
            minIfStmt = Integer.parseInt(props.getProperty("minIfStmt", DEFAULT_MIN_IFSTMT));
            minParams = Integer.parseInt(props.getProperty("minParams", DEFAULT_MIN_PARAMS));

        } catch (IOException e) {
            System.err.println("Invalid configuration file.");
            return;
        }

        System.out.println(type + " " + minExpr + " " + minIfStmt + " " + minParams);

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

        for (File repo : srcRoot.listFiles(File::isDirectory)) {

            System.out.println("Processing repository: " + repo.getName());

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
                        minParams
                );

                filter.collectJavaFiles();
                filter.collectSuitableFiles();

                ArrayList<File> suitable = filter.getSuitableFiles();

                // 3. Copy suitable files to destination
                for (File f : suitable) {
                    String newPath = f.getAbsolutePath()
                            .replace(tempRepo.getAbsolutePath(), destRoot.getAbsolutePath());

                    File destinationFile = new File(newPath);
                    destinationFile.getParentFile().mkdirs();

                    if (destinationFile.exists()) {
                        destinationFile.delete();
                    }

                    FileUtils.copyFile(f, destinationFile);
                }

                System.out.println(
                        "  Found " + suitable.size() + " suitable files out of " + filter.getJavaFiles().size()
                );

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

        // Cleanup root temp directory
        FileUtils.forceDelete(tmpRoot);

        System.out.println("Filtering complete.");
    }
}
