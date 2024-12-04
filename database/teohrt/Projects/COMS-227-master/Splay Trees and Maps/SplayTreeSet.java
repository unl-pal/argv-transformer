package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.management.RuntimeErrorException;

/**
 *
 * Splay tree implementation of the Set interface.  The contains() and
 * remove() methods of AbstractSet are overridden to search the tree without
 * using the iterator.
 *
 */
public class SplayTreeSet<E extends Comparable<? super E>> extends AbstractSet<E>
{
	//for toString
	private StringBuilder sb;
	// The root of the tree containing this set's items	
	Node<E> root;

	// The total number of elements stored in this set
	int size;

	/**
	 *
	 * Iterator implementation for this splay tree.  The elements are
	 * returned in ascending order according to their natural ordering.
	 *
	 */
	private class SplayTreeIterator implements Iterator<E>
	{
		Node<E> cursor;
	}
}

