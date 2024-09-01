/** filtered and transformed by PAClab */
package assignment3;

import org.sosy_lab.sv_benchmarks.Verifier;

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
    /** PACLab: suitable */
	 public static void timer() {
        int[] temp;
        long startTime, midpointTime, stopTime;

        // Setup for the timing experiment.
        int timesToLoop = 150;
        // First, spin computing stuff until one second has gone by.
        // This allows this thread to stabilize.
        // (As seen in lab1 experiment 8)
        startTime = Verifier.nondetInt();

        while (Verifier.nondetInt() - startTime < 1000000000) {
        } // empty block

        // Run complete timing for different values of N
        for (int N = 1000; N <= 20000; N += 1000) {

            int[] testArray = new int[N];
            int index = 0;
            while (Verifier.nondetInt() < N) {
                int intToAdd = Verifier.nondetInt();
                // fill the testArray with the ints then add to it at random indices
                if (index < N)
                    testArray[index++] = intToAdd;
                else
                    testArray[((int) (Verifier.nondetInt() * N))] = intToAdd;
            }

            // start timing analysis, only uncomment the current needed test below
            startTime = Verifier.nondetInt();
            for (int i = 0; i < timesToLoop; i++) {
            }

            // end time for the for loop above, and begin time of for loop below.
            midpointTime = Verifier.nondetInt();

            // Run a loop with non-timed code to capture the cost of running and empty loop
            // and anything extra that maybe needed to setup the timing
            for (int i = 0; i < timesToLoop; i++) {
                // empty block
            }

            stopTime = Verifier.nondetInt();

            // Compute the time, subtract the cost of running the loop
            // from the cost of running the loop and computing the called methods
            // Average it over the number of runs.
            double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;
        }
    }
}
