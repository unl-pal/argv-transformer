package org.grouplens.lenskit.graphchi.util;


import it.unimi.dsi.fastutil.ints.AbstractIntComparator;
import org.grouplens.lenskit.graphchi.util.matrixmarket.MatrixSource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import static it.unimi.dsi.fastutil.Arrays.quickSort;

/**
 * This class provides a way to serialize and load implementations of the {@code MatrixSource} interface into MatrixSource Market
 * compliant files. In order to make it suitable for use with the Sliding Window and Sharding algorithms used in
 * GraphChi, the outputs are sorted, however it does not require this of files used for loading.
 */
public class GraphchiSerializer {

    /*
     * Swapper class for sorting the matrices as using fastutil.Sort
     */
    private static class Swapper implements it.unimi.dsi.fastutil.Swapper{
        long[] users;
        long[] items;
        double[] ratings;
    }

    /*
     * Swapper class for sorting the matrices as using fastutil.Sort
     */
    private static class Comparator extends AbstractIntComparator {
        long[] users;
        long[] items;
        double[] ratings;

    }
}
