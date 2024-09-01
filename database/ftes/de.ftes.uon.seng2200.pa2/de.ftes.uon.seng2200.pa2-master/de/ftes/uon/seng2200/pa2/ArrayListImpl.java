package de.ftes.uon.seng2200.pa2;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list implementation.
 * 
 * @author Fredrik Teschke (3228760)
 *
 */
public class ArrayListImpl<T> implements ArrayList<T> {
	/**
	 * A node in a doubly linked list.
	 * @author Fredrik Teschke
	 *
	 * @param <T> type of managed data element
	 * @param <N> type of node
	 */
	private interface Node<T> {
	}
	
	/**
	 * A data node that actually holds a {@link #getData() data object}.
	 * @author Fredrik Teschke
	 *
	 */
	private class DataNodeImpl implements Node<T> {
		private final T data;
		private Node<T> next;
		private Node<T> previous;
	}
	
	/**
	 * A sentinel node that holds no data, but is merely used as the first/last node in a list
	 * to make implementation easier by using polymorphism rather than if statements.
	 * 
	 * @author Fredrik Teschke
	 *
	 */
	private abstract class AbstractSentinelNode extends DataNodeImpl {
	}
	
	/**
	 * A sentinel node used as the first node of a list that has no {@link #getPrevious() previous} node.
	 * @author Fredrik Teschke
	 *
	 */
	private class StartNodeImpl extends AbstractSentinelNode {
	}
	
	/**
	 * A sentinel node for the end of the list that has no {@code next} node.
	 * @author Fredrik Teschke
	 *
	 */
	private class EndNodeImpl extends AbstractSentinelNode {
	}

	
	private final Node<T> start = new StartNodeImpl();
	private final Node<T> end = new EndNodeImpl();
	private int size = 0;
	private int modCount = 0;
}
