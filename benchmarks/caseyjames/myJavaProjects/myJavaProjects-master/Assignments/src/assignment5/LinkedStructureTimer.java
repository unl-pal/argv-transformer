/** filtered and transformed by PAClab */
package assignment5;

import gov.nasa.jpf.symbc.Debug;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for timing analysis of the <tt>addFirst</tt>, <tt>get(i)</tt>, and <tt>remove</tt> methods from the
 * <tt>MyLinkedList</tt> class, the <tt>add</tt>, <tt>get(i)</tt>, and <tt>remove</tt> methods from a generic
 * <tt>ArrayList</tt>, and finally, timing analysis tests of the <tt>push</tt>, <tt>pop</tt>, and <tt>peek</tt> methods
 * from the <tt>MyStack</tt> class. Each method has a different way of timing that is appropriate for that particular
 * method to understand its complexity and growth rate.
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 6/19/2014
 */
public class LinkedStructureTimer {

    /**
     * Tests the <tt>addFirst</tt> method of the <tt>MyLinkedList</tt> class to determine it's running time complexity.
     */
    /** PACLab: suitable */
	 void addFirstTimer() {
        int timesToLoop = 1000;  // higher number causes more accurate average time, but takes longer
        int maxSize = 100000;   // determines right boundary of plot, or MAX inputs 'N'
        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'
            // allows i to equal 1000 then 5000 and then even 5000 increments after.
            if (i == 0) i = 1000;

            int[] randNum = new int[i * timesToLoop];
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");
            // create array of random numbers for addFirstList to add from, before any timing starts.
            for (int j = 0; j < timesToLoop * i; j++) {
                {
				}
            }

            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing starts here.
            startTime = Debug.makeSymbolicInteger("x3");

            for (int j = 0; j < timesToLoop; j++) {  //timesToLoop helps take an average of i inputs
                for (int k = 0; k < i; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x4");      // middle time is set, to subtract startTime from.

            for (int j = 0; j < timesToLoop; j++) {     // same loops without addFirst to determine overhead to subtract
                for (int k = 0; k < i; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x5");

            // determine total amount of time to add 'on average' to add i amount of inputs
            double avgTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            if (i == 1000) i = 0; // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /**
     * Test the <tt>add(int index, E element)</tt> method of an <tt>ArrayList</tt> to determine it's running time
     * complexity, as well as compare it's results that of the LinkedList.
     */
    /** PACLab: suitable */
	 void addTimer() {
        int timesToLoop = 50;  // higher number causes more accurate average time
        int maxSize = 100000;   // determines right boundary of plot
        // testing loop
        for (int i = 0; i <= maxSize; i += 5000) {   // each of these loops accounts for a different input size 'N'
            // allows i to equal 1000 then 5000 and then even 5000 increments after.
            if (i == 0) i = 1000;

            int[] randNum = new int[i * timesToLoop];
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");
            // create array of random numbers for addList to add from, before any timing starts.
            for (int j = 0; j < timesToLoop * i; j++) {
                {
				}
            }

            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = Debug.makeSymbolicInteger("x3");

            for (int j = 0; j < timesToLoop; j++) {   //timesToLoop helps take an average of i inputs
                for (int k = 0; k < i; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x4");        // middle time is set, to subtract startTime from.

            for (int j = 0; j < timesToLoop; j++) {  // same loops without addFirst to determine overhead to subtract
                for (int k = 0; k < i; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;
        }
    }

    /**
     * Tests the <tt>get(int index)</tt> method of the <tt>MyLinkedList</tt> class to determine it's running time
     * complexity.
     */
    /** PACLab: suitable */
	 void linkedGetTimer() {
        int timesToLoop = 20;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        // testing loops,each of these loops accounts for a different input size 'N'
        for (int i = 0; i <= maxSize; i += 5000) {
            if (i == 0) i = 1000; // allows i to equal 1000 then 5000 and then even 5000 increments after.

            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");  //use clock as initial starting point for the seed.
            seed = Debug.makeSymbolicInteger("x1");  //update seed,
            for (int j = 0; j < i; j++) {
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = Debug.makeSymbolicInteger("x2");
            while (Debug.makeSymbolicInteger("x3") - startTime < 1e9) {/*empty*/}

            // startTime and testing start here
            startTime = Debug.makeSymbolicInteger("x4");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                }                                           //good average time for i
            }
            midTime = Debug.makeSymbolicInteger("x5");   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {     // same loops run again without .get() to determine overhead
                for (int k = 0; k < i; k++) {
                }
            }
            endTime = Debug.makeSymbolicInteger("x6");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /**
     * Test the <tt>get</tt> method of an <tt>ArrayList</tt> to determine it's run time complexity and compare it to the
     * linked list. Expected get(i) runtime is 0(1) or constant time.
     */
    /** PACLab: suitable */
	 void arrayGetTimer() {
        int timesToLoop = 2500;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");

            /*create a non empty array first or index out of bounds exception will be thrown,
            then in the loops below each element is reset to random numbers. This is quicker than clear and
            re-adding new elements to the array.*/

            for (int j = 0; j < i; j++) {
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) {
            }

            // startTime and testing start here
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int h = 0; h < i; h++) {
                }
                for (int k = 0; k < i; k++) {
                }
            }
            midTime = Debug.makeSymbolicInteger("x4");   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {
                for (int h = 0; h < i; h++) {
                }
                for (int k = 0; k < i; k++) {
                }
            }
            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /** PACLab: suitable */
	 void linkedRemoveTimer() {
        int timesToLoop = 5;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");
            // add random numbers to linkedGetList until size equals i, this so the get method can be tested.
            for (int j = 0; j < i; j++) {
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing start here
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x4");   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {     // same loops run again without .get() to determine overhead
                for (int k = 0; k < i; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /** PACLab: suitable */
	 void arrayRemoveTimer() {
        int timesToLoop = 20;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");
            // add random numbers to linkedGetList until size equals i, this so the get method can be tested.
            for (int j = 0; j < i; j++) {
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing start here
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x4");   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {     // same loops run again without .get() to determine overhead
                for (int k = 0; k < i; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /** PACLab: suitable */
	 void pushTimer() {
        int timesToLoop = 2500;  // higher number causes more accurate average time
        int maxSize = 100000;   // determines right boundary of plot
        // testing loop
        for (int i = 0; i <= maxSize; i += 5000) {   // each of these loops accounts for a different input size 'N'
            // allows i to equal 1000 then 5000 and then even 5000 increments after.
            if (i == 0) i = 1000;

            int[] randNum = new int[i * timesToLoop];
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");
            // create array of random numbers for addList to add from, size is loops * i.
            for (int j = 0; j < timesToLoop * i; j++) {
                {
				}
            }

            // let a while loop run for a full second to get things spooled up.
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = Debug.makeSymbolicInteger("x3");

            for (int j = 0; j < timesToLoop; j++) {   //timesToLoop helps take an average of i inputs
                for (int k = 0; k < i; k++) {
                }
            }

            midTime = Debug.makeSymbolicInteger("x4");        // middle time is set, to subtract startTime from.

            for (int j = 0; j < timesToLoop; j++) {  // same loops without addFirst to determine overhead to subtract
                for (int k = 0; k < i; k++) {
                }
            }

            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;
        }
    }

    /** PACLab: suitable */
	 void popTimer() {
        int timesToLoop = 2500;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) {
            }

            // startTime and testing start here
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int h = 0; h < i; h++) {
                }
                for (int k = 0; k < i; k++) {
                }
            }
            midTime = Debug.makeSymbolicInteger("x4");   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {
                for (int h = 0; h < i; h++) {
                }
                for (int k = 0; k < i; k++) {
                }
            }
            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /** PACLab: suitable */
	 void peekTimer() {
        int timesToLoop = 9999;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            long startTime, midTime, endTime;
            long seed = Debug.makeSymbolicInteger("x0");
            for (int j = 0; j < i; j++) {
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = Debug.makeSymbolicInteger("x1");
            while (Debug.makeSymbolicInteger("x2") - startTime < 1e9) {
            }

            // startTime and testing start here
            startTime = Debug.makeSymbolicInteger("x3");
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                }
            }
            midTime = Debug.makeSymbolicInteger("x4");   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                }
            }
            endTime = Debug.makeSymbolicInteger("x5");

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }
}
