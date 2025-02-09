package integration;

import org.junit.Test;

import transform.Main;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

public class DirectoryDiffTest {

    @Test
    public void testDirectoriesAreIdentical() throws IOException {
    	Main.main(new String [] { "test/transformer/integration", "testOutput" });
        File expectedDir = new File("integrationExpected");
        File actualDir = new File("testOutput");

        assertTrue("Directories differ!", areDirectoriesEqual(expectedDir, actualDir));
    }

    /**
     * Recursively compares two directories.
     *
     * @param dir1 the first directory
     * @param dir2 the second directory
     * @return true if the directories contain the same files with identical contents; false otherwise
     * @throws IOException if an I/O error occurs
     */
    private boolean areDirectoriesEqual(File dir1, File dir2) throws IOException {
        if (!dir1.isDirectory() || !dir2.isDirectory()) {
            throw new IllegalArgumentException("Both parameters must be directories.");
        }

        File[] dir1Files = dir1.listFiles();
        File[] dir2Files = dir2.listFiles();

        if (dir1Files == null || dir2Files == null) {
            throw new IOException("Failed to list directory contents.");
        }

        // Sort file arrays by name to ensure they are compared in the same order.
        Arrays.sort(dir1Files, Comparator.comparing(File::getName));
        Arrays.sort(dir2Files, Comparator.comparing(File::getName));

        // If the number of files differs, the directories differ.
        if (dir1Files.length != dir2Files.length) {
            return false;
        }

        for (int i = 0; i < dir1Files.length; i++) {
            File file1 = dir1Files[i];
            File file2 = dir2Files[i];

            // Names must match.
            if (!file1.getName().equals(file2.getName())) {
                return false;
            }

            if (file1.isDirectory() && file2.isDirectory()) {
                // Recursively compare subdirectories.
                if (!areDirectoriesEqual(file1, file2)) {
                    return false;
                }
            } else if (file1.isFile() && file2.isFile()) {
                // Compare file contents.
                if (!areFilesEqual(file1, file2)) {
                    return false;
                }
            } else {
                // One is a file and the other is a directory.
                return false;
            }
        }
        return true;
    }

    /**
     * Compares the contents of two files.
     *
     * @param file1 the first file
     * @param file2 the second file
     * @return true if the files have the same contents; false otherwise
     * @throws IOException if an I/O error occurs
     */
    private boolean areFilesEqual(File file1, File file2) throws IOException {
        // Quick check: if file sizes differ, the files are different.
        if (file1.length() != file2.length()) {
        	assertEquals(new String(Files.readAllBytes(file1.toPath())),new String(Files.readAllBytes(file2.toPath())));
            return false;
        }

        try (InputStream is1 = new FileInputStream(file1);
             InputStream is2 = new FileInputStream(file2)) {

            int byteFile1, byteFile2;
            while ((byteFile1 = is1.read()) != -1) {
                byteFile2 = is2.read();
                if (byteFile1 != byteFile2) {
                	assertEquals(new String(Files.readAllBytes(file1.toPath())),new String(Files.readAllBytes(file2.toPath())));
                    return false;
                }
            }
            // Both streams should end at the same time.
            return is2.read() == -1;
        }
    }
}
