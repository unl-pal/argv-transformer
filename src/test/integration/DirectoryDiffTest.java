package integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import transform.Main;



@RunWith(Parameterized.class)
public class DirectoryDiffTest {

    // Static temporary directory that will be used across all tests.
    private static Path tempDir;
    // Directory with the expected output files.
    private static final Path expectedDir = Paths.get("src", "test", "integrationExpected");

    // Instance fields for the parameterized test.
    private String relativePath;
    private Path expectedFile;
    private Path actualFile;

    // Constructor that JUnit will use to inject parameters.
    public DirectoryDiffTest(String relativePath, Path expectedFile, Path actualFile) {
        this.relativePath = relativePath;
        this.expectedFile = expectedFile;
        this.actualFile = actualFile;
    }

    /**
     * Static initializer that runs once when the test class is loaded.
     * It creates the temporary directory and runs the transformation.
     */
    static {
        try {
            tempDir = Files.createTempDirectory("tmp");
            System.out.println("Temporary directory created: " + tempDir.toAbsolutePath());
            // Run the transformation so that files are written into the temp directory.
            Main.main(new String[] {Paths.get("src", "test", "transformer", "integration").toString(), tempDir.toAbsolutePath().toString()});
        } catch (IOException e) {
            throw new RuntimeException("Failed to set up temporary directory", e);
        }
    }

    /**
     * (Optional) Additional setup before any tests run.
     */
    @BeforeClass
    public static void setUpClass() {
        // The tempDir is already created and populated in the static initializer.
        System.out.println("Setup complete. Ready to run file comparisons.");
    }

    /**
     * Cleans up the temporary directory after all tests have run.
     */
    @AfterClass
    public static void tearDownClass() throws IOException {
        // Recursively delete the temporary directory.
        Files.walk(tempDir)
             .sorted(Comparator.reverseOrder()) // Delete children before parents.
             .forEach(path -> {
                 try {
                     Files.delete(path);
                     System.out.println("Deleted: " + path);
                 } catch (IOException e) {
                     System.err.println("Failed to delete: " + path + " - " + e.getMessage());
                 }
             });
        System.out.println("Temporary directory deleted successfully.");
    }

    /**
     * Creates parameters for each .java file found in the expected directory.
     * Each parameter is an array containing:
     *   [ relativePath (String), expected file (Path), corresponding actual file (Path) ]
     */
    @Parameterized.Parameters(name = "{index}: File {0}")
    public static Collection<Object[]> data() throws IOException {
        List<Object[]> parameters = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(expectedDir)) {
            stream.filter(Files::isRegularFile)
                  .filter(path -> path.toString().endsWith(".java"))
                  .forEach(expectedFile -> {
                      Path relPath = expectedDir.relativize(expectedFile);
                      Path actualFile = tempDir.resolve(relPath);
                      parameters.add(new Object[] { relPath.toString(), expectedFile, actualFile });
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
        assertEquals("Contents differ for file: " + relativePath, expectedContent, actualContent);
    }
}
