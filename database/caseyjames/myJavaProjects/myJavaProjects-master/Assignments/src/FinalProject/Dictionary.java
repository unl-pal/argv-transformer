package FinalProject;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 *
 *
 *
 */
@SuppressWarnings("ConstantConditions")
public class Dictionary {
    private Node[] dictionary;

    /**
     * Wrapper class for dictionary entries, consisting of the word and its frequency
     */
    private class Node {
        String name;
        int frequency;
        Node next;
    }
}
