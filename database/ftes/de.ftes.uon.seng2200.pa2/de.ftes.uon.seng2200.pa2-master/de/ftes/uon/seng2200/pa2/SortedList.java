package de.ftes.uon.seng2200.pa2;


/**
 * A list that sorts elements upon insertion using their {@link Comparable#compareTo} method.
 * All elements in this list will have negative {@link Comparable#compareTo compareTo} values
 * with regards to all elements to the right of them.
 * @author Fredrik Teschke (3228760)
 *
 */
public interface SortedList<T extends Comparable<T>> extends List<T> {
}
