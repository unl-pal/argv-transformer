package assignment9;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items.
 * The queue is implemented as a min heap.
 * The min heap is implemented implicitly as an array.
 *
 * @author Paymon Saebi
 * @author Casey Nordgran
 * @author Cody Cortello
 * @version 7/16/2014
 */
public class PriorityQueueHEAP<AnyType> {
    private AnyType[] array;
    private int currentSize;
    private Comparator<? super AnyType> cmp;

    /**
     * Removes and returns the minimum item in this priority queue.
     * (Runs in logarithmic time.)
     *
     * @return The minimum item in the priority queue
     * @throws java.util.NoSuchElementException if this priority queue is empty.
     */
    public AnyType deleteMin() throws NoSuchElementException {
        // first check for empty priority queue and throw exception if it is
        if (currentSize == 0)
            throw new NoSuchElementException("This priority queue is empty!");
        // store item at index 0 to be returned
        AnyType minItem = array[0];
        // set last item to index 0 and set it's previous index to null
        array[0] = array[currentSize - 1];
        array[currentSize - 1] = null;
        currentSize--;
        // now percolateDown the newly set element at index 0, and decrement size
        percolateDown(0);
        // return previous element at index 0 before the remove
        return minItem;
    }

    /**
     * percolates the item up at the specified index
     *
     * @param index array index of specified element to percolate up
     */
    private void percolateUp(int index) {
        if (index <= 0)
            return;
        // store item at specified index to be inserted at correct location instead of swaping
        AnyType item = array[index];
        // continually move next parent down to current index if item is less than parent.
        do {
            if (compare(item, array[(index - 1) / 2]) < 0) {
                array[index] = array[(index - 1) / 2];
            } else
                break;
            // update index
            index = (index - 1) / 2;
        } while ((index - 1) / 2 > 0);
        // if there is still a parent index at 0, if so compare and swap if necessary.
        if (index > 0 && (index - 1) / 2 <= 0) {
            if (compare(item, array[0]) < 0) {
                array[index] = array[0];
                array[0] = item;
                return;
            }
        }
        // after percolate up reaches correct spot, item is inserted at current index.
        array[index] = item;
    }

    /**
     * Recursive method to percolate down the specified item at the index, can be used with deleteMin
     * and buildHeap if needed.
     */
    private void percolateDown(int index) {
        // if this index has no children then return
        if (index * 2 + 1 >= currentSize)
            return;
        // in the case of 1 child
        if (index * 2 + 1 < currentSize && index * 2 + 2 >= currentSize) {
            if (compare(array[index], array[index * 2 + 1]) > 0) {
                swap(index, index * 2 + 1);
                return;
            }
            return;
        }
        // case of 2 children
        int smallerChild;
        // determine smaller of the two children
        if (compare(array[index * 2 + 1], array[index * 2 + 2]) <= 0)
            smallerChild = index * 2 + 1;
        else
            smallerChild = index * 2 + 2;
        // compare smaller child with item at current index
        if (compare(array[index], array[smallerChild]) > 0) {
            swap(index, smallerChild);
            percolateDown(smallerChild);
        }
        // if item is already in the correct position, just return
        return;
    }
}
