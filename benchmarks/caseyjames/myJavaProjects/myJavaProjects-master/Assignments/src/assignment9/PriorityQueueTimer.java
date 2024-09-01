/** filtered and transformed by PAClab */
package assignment9;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Created by Cody on 7/20/2014.
 */
public class PriorityQueueTimer {
    /** PACLab: suitable */
	 public static void PriorityHeapAddTimer() throws Exception {
        int TIMES_TO_LOOP = Verifier.nondetInt();
		boolean printAtEnd = rand.nextBoolean();
		boolean printAsRuns = rand.nextBoolean();
		int INTERVAL = Verifier.nondetInt();
		int MAX_SIZE = Verifier.nondetInt();
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
            startTime = Verifier.nondetInt();
            while (Verifier.nondetInt() - startTime < 1e9) { //empty block
            }

            startTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            midTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            endTime = Verifier.nondetInt();

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
        int TIMES_TO_LOOP = Verifier.nondetInt();
		boolean printAtEnd = rand.nextBoolean();
		boolean printAsRuns = rand.nextBoolean();
		int INTERVAL = Verifier.nondetInt();
		int MAX_SIZE = Verifier.nondetInt();
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
            startTime = Verifier.nondetInt();
            while (Verifier.nondetInt() - startTime < 1e9) { //empty block
            }

            startTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            }

            midTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
            }

            endTime = Verifier.nondetInt();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = Verifier.nondetInt() * 1e9;

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
        int TIMES_TO_LOOP = Verifier.nondetInt();
		boolean printAtEnd = rand.nextBoolean();
		boolean printAsRuns = rand.nextBoolean();
		int INTERVAL = Verifier.nondetInt();
		int MAX_SIZE = Verifier.nondetInt();
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
            startTime = Verifier.nondetInt();
            while (Verifier.nondetInt() - startTime < 1e9) { //empty block
            }

            startTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int i = 0; i < currentSize; i++) {
				}
                for (int i = 0; i < currentSize; i++) {
				}
            }

            midTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int i = 0; i < currentSize; i++) {
				}
            }

            endTime = Verifier.nondetInt();

            // calculate the total time and the average time
            double totalTime = (double) (2 * midTime - startTime - endTime) / 1e9;
            double avgTime = Verifier.nondetInt() * 1e9;

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
        int TIMES_TO_LOOP = Verifier.nondetInt();
		boolean printAtEnd = rand.nextBoolean();
		boolean printAsRuns = rand.nextBoolean();
		int INTERVAL = Verifier.nondetInt();
		int MAX_SIZE = Verifier.nondetInt();
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
            startTime = Verifier.nondetInt();
            while (Verifier.nondetInt() - startTime < 1e9) { //empty block
            }

            startTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            midTime = Verifier.nondetInt();
            for (int j = 0; j < TIMES_TO_LOOP; j++) {
                for (int k = 0; k < currentSize; k++) {
                }
            }

            endTime = Verifier.nondetInt();

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
