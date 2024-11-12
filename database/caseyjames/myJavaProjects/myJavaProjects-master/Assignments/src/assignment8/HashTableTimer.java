package assignment8;

import java.util.Random;

/**
 * class to perform the timing analysis of assignment 8
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class HashTableTimer {
    static int MAX_TABLE_SIZE = 100000;
    static int TIMES_TO_LOOP = 100;
    static int INTERVAL = 2500;
    static long seed = 82314917283974983L;

    public static void hashFunctionTime() {
        long startTime, midTime, endTime;
//        int dataIndex = 0;
        // stores values than prints at the end.
//        int[] sizeData = new int[MAX_TABLE_SIZE / INTERVAL + 1];
//        double[] totalTimeData = new double[MAX_TABLE_SIZE / INTERVAL + 1];

        //uncomment the hash function you want to test
//        HashFunctor hasher = new BadHashFunctor();
        HashFunctor hasher = new FairHashFunctor();
//        HashFunctor hasher = new GoodHashFunctor();

        // print header
        System.out.println("Timing trial for BadHashFunctor()\nHashes\tTotal time\tAverage Time");

        // table sizes
        for (int i = 0; i <= MAX_TABLE_SIZE; i += INTERVAL) {
            if (i == 0) i = 1000;

            int combined = i * TIMES_TO_LOOP;
//            String[] randomStrings = stringArray(i * TIMES_TO_LOOP);
            String[] randomStrings = stringArray(combined);
            String temp;
            int index;

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            startTime = System.nanoTime();
            index = 0;
//            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            for (int k = 0; k < combined; k++) {
                temp = randomStrings[index++];
                // this next statement is what is dependent on the hashtable size.
                int hashIndex = hasher.hash(temp) % i;
            }
//            }

            midTime = System.nanoTime();
            index = 0;
//            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            for (int k = 0; k < combined; k++) {
                temp = randomStrings[index++];
            }
//            }

            endTime = System.nanoTime();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime);
            double avgTime = totalTime / combined;

            // store the times to be printed after execution completes
//            sizeData[dataIndex] = i;
//            totalTimeData[dataIndex] = totalTime;
//            totalTimeData[dataIndex++] = avgTime;

            // uncomment this section to print the results as the test runs
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);
            if (i == 1000) i = 0;
        }
        // after testing finishes, print stored data
//        System.out.println("size\ttime");
//        for (int i = 0; i <= MAX_TABLE_SIZE / INTERVAL; i++)
//            System.out.println(sizeData[i] + "\t" + totalTimeData[i]);
    }

//     DO: conduct an experiment assess the quality and efficiency of each of your three hash functions.
//    * A recommendation for this experiment is to create two plots: one that shows the
//    growth of the number of collisions incurred by each hash function for a variety of
//    hash table sizes, and one that shows the actual running time required by each hash
//    function for a variety of hash table sizes. You may use either type of table for
//    this experiment.

//     DO: conduct an experiment assess the quality and efficiency of each of your two hash tables.
//    * A recommendation for this experiment is to create two plots: one that shows the number of
//     collisions incurred by each hash table using the hash function in GoodHashFunctor, and one
//     that shows the actual running time required by each hash table using the hash function in
//     GoodHashFunctor.
}
