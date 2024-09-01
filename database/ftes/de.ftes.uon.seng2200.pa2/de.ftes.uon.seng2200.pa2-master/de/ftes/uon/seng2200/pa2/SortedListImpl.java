package de.ftes.uon.seng2200.pa2;



/**
 * A sorted list that defers all sorting functionality to its
 * {@link ComparableNode}s.
 * 
 * @author Fredrik Teschke (3228760)
 *
 */
public class SortedListImpl<T extends Comparable<T>> extends ArrayListImpl<T>
		implements SortedList<T> {
}
