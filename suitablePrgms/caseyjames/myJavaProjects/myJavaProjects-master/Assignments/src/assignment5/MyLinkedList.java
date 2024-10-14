package assignment5;

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
public class MyLinkedList<E> implements List<E> {
    //Instance variables
    int size;
    Node head;
    Node tail;

    /**
     * Returns the element at the specified position in the list. Throws IndexOutOfBoundsException if index is out of
     * range. O(N) for a doubly-linked list.
     *
     * @param index Position of element in this list to be returned.
     * @return Element from this list located at the position of <tt>index</tt>
     * @throws IndexOutOfBoundsException
     */
    public E get(int index) throws IndexOutOfBoundsException {
        // first check for index being out of bounds
        if (size == 0 || index >= size || index < 0)
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException"); //message added for testing

        // if index is the first or last index, use getFirst or getLast instead.
        if (index == 0)
            return this.getFirst();
        if (index == size - 1)
            return this.getLast();

        //find which end the index is closest to start from
        boolean startFront = true;
        if (size - index < index) {
            startFront = false;
            index = size - index - 1;
        }

        // node to start from and traverse with.
        Node toReturn;
        // if index is closer to head, start at head
        if (startFront) {
            toReturn = head;
            for (int i = 0; i < index; i++)
                toReturn = toReturn.next;
        } else { // otherwise traverse the list backwards from the tail
            toReturn = tail;
            for (int i = 0; i < index; i++)
                toReturn = toReturn.prev;
        }
        // with toReturn node at correct index, return its associated data
        return toReturn.data;
    }

    /**
     * Removes and returns the element at the specified position in the list. Throws IndexOutOfBoundsException if
     * index is out of range. O(N) for a doubly-linked list.
     *
     * @param index Position of element in this list to be removed.
     * @return Element in this list that is removed at the index position.
     * @throws IndexOutOfBoundsException
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        // variable for item to be removed and returned
        E item;
        // check first that index is not out of bounds, if so throw exception
        if (size == 0 || index >= size || index < 0)
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException");//message added for testing

        // if index is the first or last index, use removeFirst & removeLast instead.
        if (index == 0)
            return this.removeFirst();
        if (index == size - 1)
            return this.removeLast();

        //find which end the index is closest to start from
        boolean startFront = true;
        if (size - index < index) {
            startFront = false;
            index = size - index - 1;
        }

        // node to start from and traverse with.
        Node toRemove;
        // if index is closer to head, start at head
        if (startFront) {
            toRemove = head;
            for (int i = 0; i < index; i++)
                toRemove = toRemove.next;
        } else {
            toRemove = tail;
            for (int i = 0; i < index; i++)
                toRemove = toRemove.prev;
        }

        // store element being removed to item to be returned, remove element, and decrement size.
        item = toRemove.data;
        toRemove.prev.next = toRemove.next;
        toRemove.next.prev = toRemove.prev;
        toRemove.next = null;
        toRemove.prev = null;
        size--;
        // returned stored item that was removed.
        return item;
    }

    /**
     * This class represents a node for a doubly-linked list. Each instance contains the data to be stored, and two
     * other nodes of this same class that represent references to the next & previous elements to this element.
     */
    private class Node {
        // item or element to be stored in the list
        E data;
        // two nodes of this same class to store the address of the next element and the previous element.
        Node next;
        Node prev;
    }
}
