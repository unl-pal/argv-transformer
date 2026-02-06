package strings;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class StringsTest {

    //inputs
    private static final Path testDir = Paths.get("src", "test", "strings");
    private static final Path inputDir = testDir.resolve("input");
    private static final Path stringConfig = testDir.resolve("stringTestConfig.properties");
    private static final Path configPath = Paths.get("config.properties");
    private static final Path configBackupPath = Paths.get("config.properties.bak");

    //outputs
    private static final Path filterActualDir = testDir.resolve("output/actual/filter");
    private static final Path filterExpectedDir = testDir.resolve("output/expected/filter");
    private static final Path transformActualDir = testDir.resolve("output/actual/transform");
    private static final Path transformExpectedDir = testDir.resolve("output/expected/transform");


    // Instance fields for the parameterized test.
    private final String stage;
    private final String relativePath;
    private final Path expectedFile;
    private final Path actualFile;

    // Constructor that JUnit will use to inject parameters.
    public StringsTest(String stage, String relativePath, Path expectedFile, Path actualFile) {
        this.stage = stage;
        this.relativePath = relativePath;
        this.expectedFile = expectedFile;
        this.actualFile = actualFile;
    }

    /**
     *  Run Filter and Transform on string test files.
     */
    @BeforeClass
    public static void setUpClass() {
        try {
            // Replace the config.properties file with the one for the string tests.
            if (Files.exists(configPath)) {
                Files.copy(configPath, configBackupPath, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.copy(stringConfig, configPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Using stringTestConfig.properties");

            cleanDir(filterActualDir);
            cleanDir(transformActualDir);

        } catch (IOException e) {
            throw new RuntimeException("Issue during setup: " + e.getMessage());
        }

        try {
            // Run the filter on files in src.test.strings.input.simpleTests
            filter.Main.main(new String[]{inputDir.toString(), filterActualDir.toString()});
        } catch (IOException e) {
            System.err.println("Failure while running string test Filter: " + e.getMessage());
        }
        try {
            // Run the transformer on EXPECTED files from filter step in src.test.strings.output.expected.filter
            transform.Main.main(new String[] {filterExpectedDir.toString(), transformActualDir.toString()});

        } catch (IOException e) {
            System.err.println("Failure while running string test Transform: " + e.getMessage());
        }

        System.out.println("Run complete. Ready for file comparisons.");
    }

    /**
     * Replace config.properties
     */
    @AfterClass
    public static void tearDownClass() throws IOException {
        // Restore the config.properties file.
        Files.move(configBackupPath, configPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Restored config.properties");
    }

    /**
     * Creates parameters for each file found in the expected directories.
     * Each parameter is an array containing:
     *   [ stage (String), relativePath (String), expected file (Path), corresponding actual file (Path) ]
     */
    @Parameterized.Parameters(name = "{0}: {1}")
    public static Collection<Object[]> data() throws IOException {
        List<Object[]> parameters = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(filterExpectedDir)) {
            stream.filter(Files::isRegularFile)
                  .forEach(expectedFile -> {
                      Path relPath = filterExpectedDir.relativize(expectedFile);
                      Path actualFile = filterActualDir.resolve(relPath);
                      parameters.add(new Object[] {"filter", relPath.toString(), expectedFile, actualFile });
                  });
        }

        try (Stream<Path> stream = Files.walk(transformExpectedDir)) {
            stream.filter(Files::isRegularFile)
                  .forEach(expectedFile -> {
                      Path relPath = transformExpectedDir.relativize(expectedFile);
                      Path actualFile = transformActualDir.resolve(relPath);
                      parameters.add(new Object[] {"transform", relPath.toString(), expectedFile, actualFile });
                  });
        }

        return parameters;
    }

    /**
     * Test that the expected file and the corresponding actual file have identical content.
     */
    @Test
    public void testFileEquality() throws IOException {
        // Ensure the actual file exists.
        assertTrue("Actual file " + actualFile.toAbsolutePath() + " does not exist for " + relativePath,
                   Files.exists(actualFile));

        // Read the contents of both files.
        String expectedContent = new String(Files.readAllBytes(expectedFile)).trim().replaceAll("\\s+", " ");
        String actualContent   = new String(Files.readAllBytes(actualFile)).trim().replaceAll("\\s+", " ");

        // Compare the contents.
        assertEquals(stage + " -> Contents differ for file: " + relativePath, expectedContent, actualContent);
    }

    private static void cleanDir(Path directory) throws IOException {
        // Clean up any existing outputs
        try (Stream<Path> walk = Files.walk(directory)) {
            walk.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            System.err.println("Failed to delete: " + path + " - " + e.getMessage());
                        }
                    });
        }
    }
}
