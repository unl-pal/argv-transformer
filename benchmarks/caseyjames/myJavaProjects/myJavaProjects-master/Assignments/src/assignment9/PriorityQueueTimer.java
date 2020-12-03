/** filtered and transformed by PAClab */
package assignment9;

import gov.nasa.jpf.symbc.Debug;
import java.util.Random;

/**
 * Created by Cody on 7/20/2014.
 */
public class PriorityQueueTimer {
    /** PACLab: suitable */
	 public static void PriorityHeapAddTimer() throws Exception {
        int TIMES_TO_LOOP = Debug.makeSymbolicInteger("x4");
		boolean printAtEnd = Debug.makeSymbolicBoolean("x3");
		boolean printAsRuns = Debug.makeSymbolicBoolean("x2");
		int INTERVAL = Debug.makeSymbolicInteger("x1");
		int MAX_SIZE = Debug.makeSymbolicInteger("x0");
		long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        // print header
        if (printAsRuns || printAtEnd) {
		}

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {
//			if (currentSize == 0) currentSize = 1000;

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x5");
            while (Debug.makeSymbolicInteger("x6") - startTime < 1e9) { //empty block
            }

            startTime = Debug.makeSymbolicInteger("x7");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x8");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x9");

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
            if (printAsRuns) {
			}

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        // after testing finishes, print stored data
        if (printAtEnd) {
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++) {
			}
        }
    }

    /** PACLab: suitable */
	 public static void PriorityHeapFindMinTimer() throws Exception {
        int TIMES_TO_LOOP = Debug.makeSymbolicInteger("x4");
		boolean printAtEnd = Debug.makeSymbolicBoolean("x3");
		boolean printAsRuns = Debug.makeSymbolicBoolean("x2");
		int INTERVAL = Debug.makeSymbolicInteger("x1");
		int MAX_SIZE = Debug.makeSymbolicInteger("x0");
		long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        // print header
        if (printAsRuns || printAtEnd) {
		}

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            for (int i = 0; i < currentSize; i++) {
			}

            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x5");
            while (Debug.makeSymbolicInteger("x6") - startTime < 1e9) { //empty block
            }

            startTime = Debug.makeSymbolicInteger("x7");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            }

            midTime = Debug.makeSymbolicInteger("x8");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            }

            endTime = Debug.makeSymbolicInteger("x9");

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = Debug.makeSymbolicInteger("x10") * 1e9;

            // store the times to be printed after execution completes
            if (printAtEnd) {
                sizeData[dataIndex] = currentSize;
                totalTimeData[dataIndex] = totalTime;
                totalTimeData[dataIndex++] = avgTime;
            }

            // print the results as the test runs
            if (printAsRuns) {
			}

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        // after testing finishes, print stored data
        if (printAtEnd) {
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++) {
			}
        }
    }

    /** PACLab: suitable */
	 public static void PriorityHeapDelMinTimer() throws Exception {
        int TIMES_TO_LOOP = Debug.makeSymbolicInteger("x4");
		boolean printAtEnd = Debug.makeSymbolicBoolean("x3");
		boolean printAsRuns = Debug.makeSymbolicBoolean("x2");
		int INTERVAL = Debug.makeSymbolicInteger("x1");
		int MAX_SIZE = Debug.makeSymbolicInteger("x0");
		long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        // print header
        if (printAsRuns || printAtEnd) {
		}

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x5");
            while (Debug.makeSymbolicInteger("x6") - startTime < 1e9) { //empty block
            }

            startTime = Debug.makeSymbolicInteger("x7");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int i = 0; i < currentSize; i++) {
				}
                for (int i = 0; i < currentSize; i++) {
				}
            }

            midTime = Debug.makeSymbolicInteger("x8");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int i = 0; i < currentSize; i++) {
				}
            }

            endTime = Debug.makeSymbolicInteger("x9");

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = Debug.makeSymbolicInteger("x10") * 1e9;

            // store the times to be printed after execution completes
            if (printAtEnd) {
                sizeData[dataIndex] = currentSize;
                totalTimeData[dataIndex] = totalTime;
                totalTimeData[dataIndex++] = avgTime;
            }

            // print the results as the test runs
            if (printAsRuns) {
			}

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        // after testing finishes, print stored data
        if (printAtEnd) {
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++) {
			}
        }
    }

    /** PACLab: suitable */
	 public static void PriorityBSTAddTimer() throws Exception {
        int TIMES_TO_LOOP = Debug.makeSymbolicInteger("x4");
		boolean printAtEnd = Debug.makeSymbolicBoolean("x3");
		boolean printAsRuns = Debug.makeSymbolicBoolean("x2");
		int INTERVAL = Debug.makeSymbolicInteger("x1");
		int MAX_SIZE = Debug.makeSymbolicInteger("x0");
		long startTime, midTime, endTime;
        int dataIndex = 0;

        int[] sizeData = new int[MAX_SIZE / INTERVAL + 1];
        double[] totalTimeData = new double[MAX_SIZE / INTERVAL + 1];

        // print header
        if (printAsRuns || printAtEnd) {
		}

        // table sizes
        for (int currentSize = INTERVAL; currentSize <= MAX_SIZE; currentSize += INTERVAL) {
//			if (currentSize == 0) currentSize = 1000;

            // initiate variables for timing structures
            double combined = currentSize * TIMES_TO_LOOP; // represents the number of total operations which should be timed
            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x5");
            while (Debug.makeSymbolicInteger("x6") - startTime < 1e9) { //empty block
            }

            startTime = Debug.makeSymbolicInteger("x7");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x8");
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x9");

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
            if (printAsRuns) {
			}

//			return currentSize to 0 for the first loop
//			if (currentSize == 1000) currentSize = 0;
        }
        // after testing finishes, print stored data
        if (printAtEnd) {
            for (int i = 0; i <= MAX_SIZE / INTERVAL; i++) {
			}
        }
    }
}
