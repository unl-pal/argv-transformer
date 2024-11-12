package assignment3;

import java.util.*;

/**
 *  This is a generic class that implements the Collection interface and uses an array as the
 *  base storage for items. This collection does not allow any duplicates in the array.
 *
 * @param <E> - generic type placeholder
 * @author Paymon Saebi
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 6/5/2014
 *
 */
public class ArrayBasedCollection<E> implements Collection<E> {
    E data[]; // Storage for the items in the collection
    int size; // Keep track of how many items we hold

    /**
     * Class that defines the iterator used for the ArrayBasedCollection class
     */
    private class ArrayBasedIterator implements Iterator<E> {
        int index;
        boolean canRemove;
        E toRemove;
    }
}
