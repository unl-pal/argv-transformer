package assignment3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 *         <p/>
 *         ArrayBasedCollection timing experiments.
 */
public class ArrayBasedCollectionTimer {
    /**
     * This code is refactored from Paymon's SortDemo.java code provided on the class website
     */
    public static void timer() {
        int[] temp;
        long startTime, midpointTime, stopTime;

        // Setup for the timing experiment.
        int timesToLoop = 150;
        System.out.println("N\tTime"); // this prints the column headers

        // First, spin computing stuff until one second has gone by.
        // This allows this thread to stabilize.
        // (As seen in lab1 experiment 8)
        startTime = System.nanoTime();

        while (System.nanoTime() - startTime < 1000000000) {
        } // empty block

        // Run complete timing for different values of N
        for (int N = 1000; N <= 20000; N += 1000) {

            // Generate the random array before starting the timer
            //  note: instead of permuting ascending numbers a random array is created and ensured no duplications by
            //  checking each add element against a HashSet (which the ints are also added to)
            ArrayBasedCollection<Integer> nums = new ArrayBasedCollection<Integer>(N);
            HashSet<Integer> testSet = new HashSet<Integer>();
            int[] testArray = new int[N];
            int index = 0;
            while (nums.size() < N) {
                int intToAdd = (int) (Math.random() * 100000);
                nums.add(intToAdd);  //both nums & testSet do not allow duplicates.
                testSet.add(intToAdd);

                // fill the testArray with the ints then add to it at random indices
                if (index < N)
                    testArray[index++] = intToAdd;
                else
                    testArray[((int) (Math.random() * N))] = intToAdd;
            }

            //permute to randomize the element locations
            permuteInts(testArray);
            //comparator to be used in the toSortedList method
            IntegerComparator cmp = new IntegerComparator();
            //sorted list before start time to be used with binary search
            ArrayList<Integer> sortedList = nums.toSortedList(cmp);

            // start timing analysis, only uncomment the current needed test below
            startTime = System.nanoTime();
            for (int i = 0; i < timesToLoop; i++) {
                nums.toSortedList(new IntegerComparator());
//                nums.contains(testArray[i%N]);
//                SearchUtil.binarySearch(sortedList, testArray[i % N], cmp);
            }

            // end time for the for loop above, and begin time of for loop below.
            midpointTime = System.nanoTime();

            // Run a loop with non-timed code to capture the cost of running and empty loop
            // and anything extra that maybe needed to setup the timing
            for (int i = 0; i < timesToLoop; i++) {
                // empty block
            }

            stopTime = System.nanoTime();

            // Compute the time, subtract the cost of running the loop
            // from the cost of running the loop and computing the called methods
            // Average it over the number of runs.
            double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

            System.out.println(nums.size() + "\t" + averageTime);
        }
    }
}
