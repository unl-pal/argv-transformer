package org.grouplens.lenskit.graphchi.util.matrixmarket;
import  org.grouplens.lenskit.cursors.AbstractPollingCursor;
import org.grouplens.lenskit.graphchi.util.MatrixEntry;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.regex.Pattern;

/**
 * The MatrixSource designed to read from a dense matrix in matrix market format. It wraps a file handle and
 * parses the matrix line by line.
 * Currently no support for reading a sparse matrix is provided.
 *
 * All of it's methods make no attempt to handle IO exceptions.
 *
 * @author Daniel Gratzer < danny.gratzer@gmail.com >
 */
public class BufferedReaderMatrixSource extends AbstractPollingCursor<MatrixEntry> implements MatrixSource{
    private int columns;
    private int rows;
    private int entries;
    private boolean isSorted;
    private BufferedReader inputSource;
    private MatrixEntry entry;
    private static Pattern whiteSpacePattern = Pattern.compile("\\s+");

    private int currentColumn = -1; //Starts negative to allow for initial increment in parseLine()
    private int currentRow    =  0;

}

