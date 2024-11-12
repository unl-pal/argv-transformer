package assignment9;

import java.util.Random;

/**
 * Created by Cody on 7/20/2014.
 */
public class PriorityQueueTimer {
    static long seed = 274981289975398L;
    static boolean printAsRuns = true;
    static boolean printAtEnd = false;
    static int MAX_SIZE = Integer.MAX_VALUE;
    static int INTERVAL = 5000;
    static int TIMES_TO_LOOP = 200;

    public static void PriorityHeapAddTimer() throws Exception {
        long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        PriorityQueueHEAP<Integer> pq = new PriorityQueueHEAP<Integer>();

        // print header
        if (printAsRuns || printAtEnd)
            System.out.println("Timing trial for PriorityQueueHEAP add()\nAdds\tTotal time (s)\tAverage Time (ns)");

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {
//			if (currentSize == 0) currentSize = 1000;

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            Random rand = new Random(seed);

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            startTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                pq = new PriorityQueueHEAP<Integer>();
                for (int k = 0; k < currentSize; k++) {
                    pq.add(rand.nextInt());
                }
            }

            midTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                pq = new PriorityQueueHEAP<Integer>();
                for (int k = 0; k < currentSize; k++) {
                }
            }

            endTime = System.nanoTime();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = totalTime * 1e9 / combined;

            // store the times to be printed after execution completes
            if (printAtEnd) {
                sizeData[dataIndex] = currentSize;
                totalTimeData[dataIndex] = totalTime;
                totalTimeData[dataIndex++] = avgTime;
            }

            // print the results as the test runs
            if (printAsRuns)
                System.out.println(currentSize + "\t" + totalTime + "\t" + avgTime);

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        System.out.println("Finished PQHEAP add() timing!\n");

        // after testing finishes, print stored data
        if (printAtEnd) {
            System.out.println("size\ttime");
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++)
                System.out.println(sizeData[i] + "\t" + totalTimeData[i]);
        }
    }

    public static void PriorityHeapFindMinTimer() throws Exception {
        long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        PriorityQueueHEAP<Integer> pq = new PriorityQueueHEAP<Integer>();

        // print header
        if (printAsRuns || printAtEnd)
            System.out.println("Timing trial for PriorityQueueHEAP findMin()\nTimes to loop = " + TIMES_TO_LOOP +
                    "\nfindMin() executions\tTotal time (s)\tAverage Time (ns)");

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            Random rand = new Random(seed);

            pq = new PriorityQueueHEAP<Integer>();
            for (int i = 0; i < currentSize; i++)
                pq.add(rand.nextInt());

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            startTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                pq.findMin();
            }

            midTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            }

            endTime = System.nanoTime();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = totalTime / combined * 1e9;

            // store the times to be printed after execution completes
            if (printAtEnd) {
                sizeData[dataIndex] = currentSize;
                totalTimeData[dataIndex] = totalTime;
                totalTimeData[dataIndex++] = avgTime;
            }

            // print the results as the test runs
            if (printAsRuns)
                System.out.println(currentSize + "\t" + totalTime + "\t" + avgTime);

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        System.out.println("Finished PQHEAP findMin() timing!\n");
        // after testing finishes, print stored data
        if (printAtEnd) {
            System.out.println("size\ttime");
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++)
                System.out.println(sizeData[i] + "\t" + totalTimeData[i]);
        }
    }

    public static void PriorityHeapDelMinTimer() throws Exception {
        long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        PriorityQueueHEAP<Integer> pq = new PriorityQueueHEAP<Integer>();

        // print header
        if (printAsRuns || printAtEnd)
            System.out.println("Timing trial for PriorityQueueHEAP delMin()\nTimes to loop = " + TIMES_TO_LOOP +
                    "\ndelMin() executions\tTotal time (s)\tAverage Time (ns)");

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            Random rand = new Random(seed);

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            startTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                pq = new PriorityQueueHEAP<Integer>();
                for (int i = 0; i < currentSize; i++)
                    pq.add(rand.nextInt());
                for (int i = 0; i < currentSize; i++)
                    pq.deleteMin();
            }

            midTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                pq = new PriorityQueueHEAP<Integer>();
                for (int i = 0; i < currentSize; i++)
                    pq.add(rand.nextInt());
            }

            endTime = System.nanoTime();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = totalTime / combined * 1e9;

            // store the times to be printed after execution completes
            if (printAtEnd) {
                sizeData[dataIndex] = currentSize;
                totalTimeData[dataIndex] = totalTime;
                totalTimeData[dataIndex++] = avgTime;
            }

            // print the results as the test runs
            if (printAsRuns)
                System.out.println(currentSize + "\t" + totalTime + "\t" + avgTime);

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        System.out.println("Finished PQHEAP delMin() timing!\n");
        // after testing finishes, print stored data
        if (printAtEnd) {
            System.out.println("size\ttime");
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++)
                System.out.println(sizeData[i] + "\t" + totalTimeData[i]);
        }
    }

    public static void PriorityBSTAddTimer() throws Exception {
        long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        PriorityQueueBST<Integer> bst = new PriorityQueueBST<Integer>();

        // print header
        if (printAsRuns || printAtEnd)
            System.out.println("Timing trial for PriorityQueueBST add()\nAdds\tTotal time (s)\tAverage Time (ns)");

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {
//			if (currentSize == 0) currentSize = 1000;

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            Random rand = new Random(seed);

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            startTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                bst = new PriorityQueueBST<Integer>();
                for (int k = 0; k < currentSize; k++) {
                    bst.add(rand.nextInt());
                }
            }

            midTime = System.nanoTime();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                bst = new PriorityQueueBST<Integer>();
                for (int k = 0; k < currentSize; k++) {
                }
            }

            endTime = System.nanoTime();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = totalTime * 1e9 / combined;

            // store the times to be printed after execution completes
            if (printAtEnd) {
                sizeData[dataIndex] = currentSize;
                totalTimeData[dataIndex] = totalTime;
                totalTimeData[dataIndex++] = avgTime;
            }

            // print the results as the test runs
            if (printAsRuns)
                System.out.println(currentSize + "\t" + totalTime + "\t" + avgTime);

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        System.out.println("Finished PQBST add() timing!\n");

        // after testing finishes, print stored data
        if (printAtEnd) {
            System.out.println("size\ttime");
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++)
                System.out.println(sizeData[i] + "\t" + totalTimeData[i]);
        }
    }
}
