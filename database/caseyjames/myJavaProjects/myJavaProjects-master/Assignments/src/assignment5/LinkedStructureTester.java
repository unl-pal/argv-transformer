package assignment5;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * A JUnit testing class to test the methods of MyLinkedList class, and the MyStack class at the end with the
 * testBulkTest() method. This JUnit test does not use the setUp or tearDown methods as each test, for the most part,
 * uses a slightly different linked list of data. Therefore, list assignments are done in the test methods.
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 6/16/2014
 */
public class LinkedStructureTester extends TestCase {
    // list for testing that contains objects of type String
    MyLinkedList<String> testListString = new MyLinkedList<String>();
    // second list for testing that will have no elements added to it.
    MyLinkedList<String> testListEmpty = new MyLinkedList<String>();

    // this bulk test always as 1 extra element on stack compared to the array
    public void testBulkTest() throws Exception {
        int initialIntegers = 100000, moves = 100000;
        Random rand = new Random(26491324791L);
        MyStack<Integer> stack = new MyStack<Integer>();
        ArrayList<Integer> addedInts = new ArrayList<Integer>();
        for (int i = 0; i < initialIntegers; i++) {
            Integer intToAdd = rand.nextInt();
            stack.push(intToAdd);
            addedInts.add(intToAdd);
        }

        for (int i = 0; i < moves; i++) {
            int next = rand.nextInt();
            if (next % 2 == 0) {
                Integer pushInt = rand.nextInt();
                stack.push(pushInt);
                addedInts.add(pushInt);
            } else if (next % 2 == 1) {
                stack.pop();
                addedInts.remove(addedInts.size() - 1);
            }
        }
//        System.out.println("Integers added = \n" + addedInts.size());
//        System.out.println("\nCurrent stack size:\n" + stack.size());
        assertEquals(addedInts.size(), stack.size());
    }
}