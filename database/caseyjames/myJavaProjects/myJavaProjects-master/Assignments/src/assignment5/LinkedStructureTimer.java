package assignment5;

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
    void addFirstTimer() {
        int timesToLoop = 1000;  // higher number causes more accurate average time, but takes longer
        int maxSize = 100000;   // determines right boundary of plot, or MAX inputs 'N'
        Random rand = new Random();  // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime");

        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'
            // allows i to equal 1000 then 5000 and then even 5000 increments after.
            if (i == 0) i = 1000;

            // declare necessary variables and lists for testing
            MyLinkedList<Integer> addFirstList = new MyLinkedList<Integer>();
            int[] randNum = new int[i * timesToLoop];
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // create array of random numbers for addFirstList to add from, before any timing starts.
            for (int j = 0; j < timesToLoop * i; j++) {
                randNum[j] = rand.nextInt();
            }

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing starts here.
            startTime = System.nanoTime();

            for (int j = 0; j < timesToLoop; j++) {  //timesToLoop helps take an average of i inputs
                for (int k = 0; k < i; k++) {               //number of inputs depends on i
                    addFirstList.addFirst(randNum[j * k]);
                }
                addFirstList = new MyLinkedList<Integer>();
            }

            midTime = System.nanoTime();      // middle time is set, to subtract startTime from.

            for (int j = 0; j < timesToLoop; j++) {     // same loops without addFirst to determine overhead to subtract
                for (int k = 0; k < i; k++) {
                }
                addFirstList = new MyLinkedList<Integer>();
            }

            endTime = System.nanoTime();

            // determine total amount of time to add 'on average' to add i amount of inputs
            double avgTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            System.out.println(i + "\t" + avgTime);     // print results to screen

            if (i == 1000) i = 0; // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /**
     * Test the <tt>add(int index, E element)</tt> method of an <tt>ArrayList</tt> to determine it's running time
     * complexity, as well as compare it's results that of the LinkedList.
     */
    void addTimer() {
        int timesToLoop = 50;  // higher number causes more accurate average time
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loop
        for (int i = 0; i <= maxSize; i += 5000) {   // each of these loops accounts for a different input size 'N'
            // allows i to equal 1000 then 5000 and then even 5000 increments after.
            if (i == 0) i = 1000;

            // declare necessary variables and lists for testing
            ArrayList<Integer> addList = new ArrayList<Integer>(i);
            int[] randNum = new int[i * timesToLoop];
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // create array of random numbers for addList to add from, before any timing starts.
            for (int j = 0; j < timesToLoop * i; j++) {
                randNum[j] = rand.nextInt();
            }

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = System.nanoTime();

            for (int j = 0; j < timesToLoop; j++) {   //timesToLoop helps take an average of i inputs
                for (int k = 0; k < i; k++) {         //number of inputs depends on i
                    addList.add(0, randNum[j * k]);
                }
                addList = new ArrayList<Integer>(i);
            }

            midTime = System.nanoTime();        // middle time is set, to subtract startTime from.

            for (int j = 0; j < timesToLoop; j++) {  // same loops without addFirst to determine overhead to subtract
                for (int k = 0; k < i; k++) {
                }
                addList = new ArrayList<Integer>(i);
            }

            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;
        }
    }

    /**
     * Tests the <tt>get(int index)</tt> method of the <tt>MyLinkedList</tt> class to determine it's running time
     * complexity.
     */
    void linkedGetTimer() {
        int timesToLoop = 20;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loops,each of these loops accounts for a different input size 'N'
        for (int i = 0; i <= maxSize; i += 5000) {
            if (i == 0) i = 1000; // allows i to equal 1000 then 5000 and then even 5000 increments after.

            // declare necessary variables, or clear existing ones to start fresh
            MyLinkedList<Integer> linkedGetList = new MyLinkedList<Integer>();
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();  //use clock as initial starting point for the seed.
            rand.setSeed(seed);
            seed = System.currentTimeMillis();  //update seed,
            linkedGetList.clear();
            for (int j = 0; j < i; j++) {            // add random numbers to linkedGetList until size equals i
                linkedGetList.addLast(rand.nextInt());
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) {/*empty*/}

            // startTime and testing start here
            startTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                    linkedGetList.get(rand.nextInt(i));  //'get' will be called i times for timesToLoop times for a
                }                                           //good average time for i
            }
            midTime = System.nanoTime();   // midTime is set to aid in subtracting overhead
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {     // same loops run again without .get() to determine overhead
                for (int k = 0; k < i; k++) {
                    rand.nextInt(i);
                }
            }
            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    /**
     * Test the <tt>get</tt> method of an <tt>ArrayList</tt> to determine it's run time complexity and compare it to the
     * linked list. Expected get(i) runtime is 0(1) or constant time.
     */
    void arrayGetTimer() {
        int timesToLoop = 2500;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            // declare necessary variables and lists for testing
            ArrayList<Integer> arrayGetList = new ArrayList<Integer>(i);
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();

            /*create a non empty array first or index out of bounds exception will be thrown,
            then in the loops below each element is reset to random numbers. This is quicker than clear and
            re-adding new elements to the array.*/

            for (int j = 0; j < i; j++) {
                arrayGetList.add(1);
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) {
            }

            // startTime and testing start here
            startTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                for (int h = 0; h < i; h++) {
                    arrayGetList.set(h, rand.nextInt());
                }
                for (int k = 0; k < i; k++) {
                    arrayGetList.get(rand.nextInt(i)); // use remove method first with the list at right N size
                }
            }
            midTime = System.nanoTime();   // midTime is set to aid in subtracting overhead
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                for (int h = 0; h < i; h++) {
                    arrayGetList.set(h, rand.nextInt());
                }
                for (int k = 0; k < i; k++) {
                    rand.nextInt(i);            //this is also calculated as it is not part of the get() method
                }
            }
            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    void linkedRemoveTimer() {
        int timesToLoop = 5;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            // declare necessary variables and lists for testing
            MyLinkedList<Integer> linkedRemoveList = new MyLinkedList<Integer>();
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // add random numbers to linkedGetList until size equals i, this so the get method can be tested.
            for (int j = 0; j < i; j++) {
                linkedRemoveList.addFirst(rand.nextInt());
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here
            startTime = System.nanoTime();
            rand.setSeed(seed);                 // set seed at beginning of tests, than again after midTime
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                    linkedRemoveList.remove(rand.nextInt(i)); // use remove method first with the list at right N size
                    linkedRemoveList.addFirst((int) (Math.random() * i)); // add int back, not using rand for consistency
                }
            }

            midTime = System.nanoTime();   // midTime is set to aid in subtracting overhead
            rand.setSeed(seed);             // reset with same seed as before for consistency
            for (int j = 0; j < timesToLoop; j++) {     // same loops run again without .get() to determine overhead
                for (int k = 0; k < i; k++) {
                    rand.nextInt(i);
                    linkedRemoveList.addFirst((int) (Math.random() * i)); //reinclude all necessary overhead to subtract
                }
            }

            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    void arrayRemoveTimer() {
        int timesToLoop = 20;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            // declare necessary variables and lists for testing
            ArrayList<Integer> arrayRemoveList = new ArrayList<Integer>(i);
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // add random numbers to linkedGetList until size equals i, this so the get method can be tested.
            for (int j = 0; j < i; j++) {
                arrayRemoveList.add(rand.nextInt());
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here
            startTime = System.nanoTime();
            rand.setSeed(seed);                 // set seed at beginning of tests, than again after midTime
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                    arrayRemoveList.remove(rand.nextInt(i)); // use remove method first with the list at right N size
                    arrayRemoveList.add((int) (Math.random() * i)); // add int back, not using rand for consistency
                }
            }

            midTime = System.nanoTime();   // midTime is set to aid in subtracting overhead
            rand.setSeed(seed);             // reset with same seed as before for consistency
            for (int j = 0; j < timesToLoop; j++) {     // same loops run again without .get() to determine overhead
                for (int k = 0; k < i; k++) {
                    rand.nextInt(i);
                    arrayRemoveList.add((int) (Math.random() * i)); //reinclude all necessary overhead to subtract
                }
            }

            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    void pushTimer() {
        int timesToLoop = 2500;  // higher number causes more accurate average time
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loop
        for (int i = 0; i <= maxSize; i += 5000) {   // each of these loops accounts for a different input size 'N'
            // allows i to equal 1000 then 5000 and then even 5000 increments after.
            if (i == 0) i = 1000;

            // declare necessary variables and lists for testing
            MyStack<Integer> pushTimeList = new MyStack<Integer>();
            int[] randNum = new int[i * timesToLoop];
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            // create array of random numbers for addList to add from, size is loops * i.
            for (int j = 0; j < timesToLoop * i; j++) {
                randNum[j] = rand.nextInt();
            }

            // let a while loop run for a full second to get things spooled up.
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) { //empty block
            }

            // startTime and testing start here.
            startTime = System.nanoTime();

            for (int j = 0; j < timesToLoop; j++) {   //timesToLoop helps take an average of i inputs
                for (int k = 0; k < i; k++) {         //number of inputs depends on i
                    pushTimeList.push(randNum[j * k]);
                }
                pushTimeList = new MyStack<Integer>();
            }

            midTime = System.nanoTime();        // middle time is set, to subtract startTime from.

            for (int j = 0; j < timesToLoop; j++) {  // same loops without addFirst to determine overhead to subtract
                for (int k = 0; k < i; k++) {
                }
                pushTimeList = new MyStack<Integer>();
            }

            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;
        }
    }

    void popTimer() {
        int timesToLoop = 2500;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            // declare necessary variables and lists for testing
            MyStack<Integer> popTimingList;
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) {
            }

            // startTime and testing start here
            startTime = System.nanoTime();
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                popTimingList = new MyStack<Integer>();
                for (int h = 0; h < i; h++) {
                    popTimingList.push(rand.nextInt());
                }
                for (int k = 0; k < i; k++) {
                    popTimingList.pop(); // use remove method first with the list at right N size
                }
            }
            midTime = System.nanoTime();   // midTime is set to aid in subtracting overhead
            rand.setSeed(seed);
            for (int j = 0; j < timesToLoop; j++) {
                popTimingList = new MyStack<Integer>();
                for (int h = 0; h < i; h++) {
                    popTimingList.push(rand.nextInt());
                }
                for (int k = 0; k < i; k++) {
                }
            }
            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }

    void peekTimer() {
        int timesToLoop = 9999;  // higher number causes more accurate average time, but takes much longer
        int maxSize = 100000;   // determines right boundary of plot
        Random rand = new Random(); // used to create random lists
        MyStack<Integer> peekTimingList;

        //print info for the max input size and the number of times looping, as well as column headers for results
        System.out.println("MaxSize = " + maxSize + ", loops = " + timesToLoop + "\n\nsize\ttime\tavgTime");

        // testing loops
        for (int i = 0; i <= maxSize; i += 5000) {  // each of these loops accounts for a different input size 'N'

            if (i == 0) i = 1000;// allows i to equal 1000 then 5000 and then even 5000 increments after.
            // declare necessary variables and lists for testing
            peekTimingList = new MyStack<Integer>();
            long startTime, midTime, endTime;
            long seed = System.currentTimeMillis();
            rand.setSeed(seed);

            for (int j = 0; j < i; j++) {
                peekTimingList.push(rand.nextInt());
            }

            // this while loop runs for a full second to get things warmed up and running before timing starts
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1e9) {
            }

            // startTime and testing start here
            startTime = System.nanoTime();
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                    peekTimingList.peek();
                }
            }
            midTime = System.nanoTime();   // midTime is set to aid in subtracting overhead
            for (int j = 0; j < timesToLoop; j++) {
                for (int k = 0; k < i; k++) {
                }
            }
            endTime = System.nanoTime();

            // subtract the over head and determine average time for 'i' calls to get.
            double totalTime = ((midTime - startTime) - (endTime - midTime)) / timesToLoop;
            double avgTime = totalTime / i;
            System.out.println(i + "\t" + totalTime + "\t" + avgTime);     // print results

            if (i == 1000) i = 0;  // used to allows 1000 to 5000 then even increments of 5000
        }
    }
}
