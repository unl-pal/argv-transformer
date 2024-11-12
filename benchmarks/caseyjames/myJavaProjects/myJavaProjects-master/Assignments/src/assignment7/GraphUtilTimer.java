/** filtered and transformed by PAClab */
package assignment7;

import gov.nasa.jpf.symbc.Debug;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
            long seed = Debug.makeSymbolicInteger("x0");
            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    // though same graph use different random key names
                    while (Debug.makeSymbolicBoolean("x4")) {
					}
                    while (Debug.makeSymbolicBoolean("x5")) {
					}
                }
            }
            // take the middle time, then run it all over again with out breadthFirstSearch to determine overhead
            midTime = Debug.makeSymbolicInteger("x6");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    while (Debug.makeSymbolicBoolean("x7")) {
					}
                    while (Debug.makeSymbolicBoolean("x8")) {
					}
                }
            }
            endTime = Debug.makeSymbolicInteger("x9");

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
            long seed = Debug.makeSymbolicInteger("x0");
            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    // though same graph use different random key names
                    while (Debug.makeSymbolicBoolean("x4")) {
					}
                    while (Debug.makeSymbolicBoolean("x5")) {
					}
                }
            }
            // take the middle time, then run it all over again with out breadthFirstSearch to determine overhead
            midTime = Debug.makeSymbolicInteger("x6");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < 5; k++) {
                    while (Debug.makeSymbolicBoolean("x7")) {
					}
                    while (Debug.makeSymbolicBoolean("x8")) {
					}
                }
            }
            endTime = Debug.makeSymbolicInteger("x9");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            // this is the avg time per use of breadthFirstSearch call
            double avgTime = totalTime / 5;
            if (i == 100) i = 0;
        }
    }
}

