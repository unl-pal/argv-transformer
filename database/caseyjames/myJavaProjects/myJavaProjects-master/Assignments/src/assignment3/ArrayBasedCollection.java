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
     * Double the capacity of the current array (maintains the elements and their ordering)
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        // This is a helper function specific to ArrayCollection
        // Doubles the size of the data storage array, retaining its current contents.
        // You will need to use something similar to the code in the constructor above to create a new array.

        // if the size is zero increase the size to 1
        if (data.length == 0) {
            E newData[] = (E[]) new Object[1];
            data = newData;
            return;
        }

        // for normal growth create a new array of twice the capacity
        E newData[] = (E[]) new Object[this.data.length * 2];

        // copy all elements to the new, twice as large array
        for (int i = 0; i < size; i++)
            newData[i] = data[i];

        // swap the smaller array with the substantiated bigger one
        this.data = newData;
    }

    /**
     * Class that defines the iterator used for the ArrayBasedCollection class
     */
    private class ArrayBasedIterator implements Iterator<E> {
        int index;
        boolean canRemove;
        E toRemove;
    }
}
