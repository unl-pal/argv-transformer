package assignment4;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Class for testing the RecursiveSortingUtility class
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 6/12/2014
 */
public class RecursiveSortingTester extends TestCase {

    /// all of the ArrayLists of Integer type used in each method for testing
    ArrayList<Integer> mergeSortList, quickSortList, pivotStrategyList, bestCaseArray, worstCaseArray,
            swapElementsList, expectedSwapArray;

    // listSize is defines generated list size as well as range of values
    // numTries is for testing some methods multiple times to average the results
    // threshold can be changed on the fly in setUp() and is set in the setMergeSortThreshold test method
    int listSize, numTries, threshold;
    // known arrays to assign to ArrayLists so expected values can be known
    int[] arr, arr2, arr3;
    // variables keeping track of number of tests in generateAverageCase test, used to find average number of inversions
    int testCount, inversionSum;
}