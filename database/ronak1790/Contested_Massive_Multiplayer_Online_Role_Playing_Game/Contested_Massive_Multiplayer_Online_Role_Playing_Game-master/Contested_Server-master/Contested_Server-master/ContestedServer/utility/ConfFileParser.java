package utility;

// Java Imports
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * The ConfFileParser class will parse any given formatted text file for
 * configuration variables.
 */
public class ConfFileParser {

    private String fileName;
    private FileInputStream fis;
    private BufferedReader bur;
}