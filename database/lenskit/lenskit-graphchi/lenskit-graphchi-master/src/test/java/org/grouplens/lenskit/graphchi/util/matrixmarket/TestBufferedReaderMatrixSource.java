package org.grouplens.lenskit.graphchi.util.matrixmarket;

import org.grouplens.lenskit.graphchi.util.MatrixEntry;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;

public class TestBufferedReaderMatrixSource {

    private static String filename1 = "testbufferedreader-nodata.txt.tmp";
    private static String filename2 = "testbufferedreader-metadata.txt.tmp";
    private static ArrayList<Double> data;

    private static final double EPSILON = 1e-6;

    private BufferedReaderMatrixSource metaDataSource;
    private BufferedReaderMatrixSource noDataSource;

    private int rows;
    private int columns;
    private int entries;

}
