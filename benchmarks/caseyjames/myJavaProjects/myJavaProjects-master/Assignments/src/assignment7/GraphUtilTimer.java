/** filtered and transformed by PAClab */
package assignment7;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class GraphUtilTimer {
    /** PACLab: suitable */
	 void DFStimer() {
        int timesToLoop = 100;  // higher number causes more accurate average time
        int maxSize = 10000;   // determines right boundary of plot
        // testing loop
        for (int i = 0; i <= maxSize; i += 1000) {
            //this allows to have the first data be 100, but then go in increments of 1000 and ending at 10000
            if (i == 0) i = 100;
            long startTime, midTime, endTime;
            long seed = Verifier.nondetInt();
            // let a while loop run for a full second to get things spooled up.
            startTime = Verifier.nondetInt();
            while (Verifier.nondetInt() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = Verifier.nondetInt();
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    // though same graph use different random key names
                    while (Verifier.nondetBoolean()) {
					}
                    while (Verifier.nondetBoolean()) {
					}
                }
            }
            // take the middle time, then run it all over again with out breadthFirstSearch to determine overhead
            midTime = Verifier.nondetInt();
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    while (Verifier.nondetBoolean()) {
					}
                    while (Verifier.nondetBoolean()) {
					}
                }
            }
            endTime = Verifier.nondetInt();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            // this is the avg time per use of breadthFirstSearch call
            double avgTime = totalTime / 5;
            if (i == 100) i = 0;
        }
    }

    /** PACLab: suitable */
	 void BFStimer() {
        int timesToLoop = 100;  // higher number causes more accurate average time
        int maxSize = 10000;   // determines right boundary of plot
        // testing loop
        for (int i = 0; i <= maxSize; i += 1000) {
            //this allows to have the first data be 100, but then go in increments of 1000 and ending at 10000
            if (i == 0) i = 100;
            long startTime, midTime, endTime;
            long seed = Verifier.nondetInt();
            // let a while loop run for a full second to get things spooled up.
            startTime = Verifier.nondetInt();
            while (Verifier.nondetInt() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = Verifier.nondetInt();
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    // though same graph use different random key names
                    while (Verifier.nondetBoolean()) {
					}
                    while (Verifier.nondetBoolean()) {
					}
                }
            }
            // take the middle time, then run it all over again with out breadthFirstSearch to determine overhead
            midTime = Verifier.nondetInt();
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    while (Verifier.nondetBoolean()) {
					}
                    while (Verifier.nondetBoolean()) {
					}
                }
            }
            endTime = Verifier.nondetInt();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            // this is the avg time per use of breadthFirstSearch call
            double avgTime = totalTime / 5;
            if (i == 100) i = 0;
        }
    }
}

