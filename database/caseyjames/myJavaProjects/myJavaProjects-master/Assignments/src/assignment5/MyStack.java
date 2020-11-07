package assignment5;

import java.util.NoSuchElementException;

/**
 * Represents a generic stack (Last in, first out). It is implemented by the underlying linked list <tt>MyLinkedList</tt>
 * and many of the methods simply call the equivalent linked list method.
 *
 * @param <E> - the type of elements contained in the stack
 *
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 *
 * @version 6/16/2014
 */
public class MyStack<E> {
    // the stack uses MyLinkedList to implement it.
    private MyLinkedList<E> linkedListStack;
}
