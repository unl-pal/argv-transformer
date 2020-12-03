/** filtered and transformed by PAClab */
package assignment5;

import gov.nasa.jpf.symbc.Debug;
import java.util.NoSuchElementException;

/**
 * Represents a generic doubly linked list.
 *
 * @param <E> - the type of elements contained in the linked list
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 6/16/2014
 */
public class MyLinkedList<E> {
    /**
     * Returns the element at the specified position in the list. Throws IndexOutOfBoundsException if index is out of
     * range. O(N) for a doubly-linked list.
     *
     * @param index Position of element in this list to be returned.
     * @return Element from this list located at the position of <tt>index</tt>
     * @throws IndexOutOfBoundsException
     */
    /** PACLab: suitable */
	 public Object get(int index) throws Exception {
        int size = Debug.makeSymbolicInteger("x0");
		// first check for index being out of bounds
        if (size == 0 || index >= size || index < 0)
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException"); //message added for testing

        // if index is the first or last index, use getFirst or getLast instead.
        if (index == 0)
            return new Object();
        if (index == size - 1)
            return new Object();

        //find which end the index is closest to start from
        boolean startFront = true;
        if (size - index < index) {
            startFront = false;
            index = size - index - 1;
        }

        // if index is closer to head, start at head
        if (startFront) {
            for (int i = 0; i < index; i++) {
			}
        } else { // otherwise traverse the list backwards from the tail
            for (int i = 0; i < index; i++) {
			}
        }
        // with toReturn node at correct index, return its associated data
        return new Object();
    }

    /**
     * Removes and returns the element at the specified position in the list. Throws IndexOutOfBoundsException if
     * index is out of range. O(N) for a doubly-linked list.
     *
     * @param index Position of element in this list to be removed.
     * @return Element in this list that is removed at the index position.
     * @throws IndexOutOfBoundsException
     */
    /** PACLab: suitable */
	 public Object remove(int index) throws Exception {
        int size = Debug.makeSymbolicInteger("x0");
		// check first that index is not out of bounds, if so throw exception
        if (size == 0 || index >= size || index < 0)
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");//message added for testing

        // if index is the first or last index, use removeFirst & removeLast instead.
        if (index == 0)
            return new Object();
        if (index == size - 1)
            return new Object();

        //find which end the index is closest to start from
        boolean startFront = true;
        if (size - index < index) {
            startFront = false;
            index = size - index - 1;
        }

        // if index is closer to head, start at head
        if (startFront) {
            for (int i = 0; i < index; i++) {
			}
        } else {
            for (int i = 0; i < index; i++) {
			}
        }

        size--;
        // returned stored item that was removed.
        return new Object();
    }

    /**
     * This class represents a node for a doubly-linked list. Each instance contains the data to be stored, and two
     * other nodes of this same class that represent references to the next & previous elements to this element.
     */
    private class Node {
    }
}
