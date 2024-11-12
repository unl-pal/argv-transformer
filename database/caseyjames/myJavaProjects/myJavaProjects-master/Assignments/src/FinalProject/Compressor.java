package FinalProject;

import java.io.*;
import java.util.Comparator;

/**
 * Created by Casey on 7/28/2014.
 */
public class Compressor {
    private File srcFile;
    private File dstFile;

    private static class CharNode {
        private char data;
        private int freq;
        private CharNode left;
        private CharNode right;
        private CharNode parent;
        private String encoding;
    }

    private static class CharNodeComparator implements Comparator<CharNode> {
    }
}
