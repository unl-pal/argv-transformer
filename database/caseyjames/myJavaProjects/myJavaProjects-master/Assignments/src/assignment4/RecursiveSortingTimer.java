package assignment4;//package assignment4;
//
//import java.util.ArrayList;
//
///**
// * Implements and times the  algorithm
// *
// * @author Cody Cortello
// * @author Casey Nordgran
// * @version 6/12/2014
// */
//public class RecursiveSortingTimer {
//    int arraySize = 1000;
//    long timesToLoop = 100;
//
//    public static void main(String[] args) {
//        new RecursiveSortingTimer();
//    }
//
//    RecursiveSortingTimer() {
//
//        System.out.println("Size = " + arraySize + ", loops = " + timesToLoop + "\n\nTime\tThreshold");
//
//        ArrayList<Integer> sortList = RecursiveSortingUtility.generateAverageCase(arraySize);
//        ArrayList<Integer> testArray;
//
//
//
//
//        // loop through cutoff values and find the average time and number of comparisons for sorting an array of size
//        // 100,000 with that cutoff value, then print the results
//        for (int i = 2; i < 30; i++) {
//
//            // change the cutoff value
//            RecursiveSortingUtility.setMergeSortThreshold(i);
//
//    		/* timing code modified from Peter Jensen's TimingExperiment08.java from his CS 2420 class of Spring 2014 */
//
//            long startTime, midpointTime, stopTime;
//
//            // First, spin computing stuff until one second has gone by.
//            // This allows this thread to stabilize.
//
//            startTime = System.nanoTime();
//            while (System.nanoTime() - startTime < 1000000000) { // empty block
//            }
//
//            // Now, run the test.
//            startTime = System.nanoTime();
//            for (long j = 0; j < timesToLoop; j++) {
//                // create a random array of data and sort it
//                testArray = new ArrayList<Integer>(sortList);
//                RecursiveSortingUtility.mergeSortDriver(sortList);
//            }
//
//            midpointTime = System.nanoTime();
//
//            // Calculate the cost of looping
//
//            for (long j = 0; j < timesToLoop; j++) {
//                testArray = new ArrayList<Integer>(sortList);
//            }
//
//            stopTime = System.nanoTime();
//
//            // Compute the time, subtract the cost of running the loop
//            // from the cost of running the loop and sorting the array.
//            // Average it over the number of runs.
//            double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;
//            averageTime /= 1.0e9;
//
//            // print out tab-delimited results
//            System.out.println(averageTime + "\t" + RecursiveSortingUtility.getMergesortThreshold());
//        }
//    }
//}
