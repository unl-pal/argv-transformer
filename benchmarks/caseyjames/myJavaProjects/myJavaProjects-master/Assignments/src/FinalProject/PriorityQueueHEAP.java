/** filtered and transformed by PAClab */
package FinalProject;

import org.sosy_lab.sv_benchmarks.Verifier;

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
    /**
     * Removes and returns the minimum item in this priority queue.
     * (Runs in logarithmic time.)
     *
     * @return The minimum item in the priority queue
     * @throws java.util.NoSuchElementException if this priority queue is empty.
     */
    public Object deleteMin() throws Exception {
        int currentSize = Verifier.nondetInt();
		// first check for empty priority queue and throw exception if it is
        if (currentSize == 0)
            throw new NoSuchElementException("This priority queue is empty!");
        currentSize--;
        // return previous element at index 0 before the remove
        return new Object();
    }

    /**
     * percolates the item up at the specified index
     *
     * @param index array index of specified element to percolate up
     */
    /** PACLab: suitable */
	 private void percolateUp(int index) {
        if (index <= 0)
            return;
        // continually move next parent down to current index if item is less than parent.
        do {
            if (Verifier.nondetInt() < 0) {
            } else
                break;
            // update index
            index = (index - 1) / 2;
        } while ((index - 1) / 2 > 0);
        // if there is still a parent index at 0, if so compare and swap if necessary.
        if (index > 0 && (index - 1) / 2 <= 0) {
            if (Verifier.nondetInt() < 0) {
                return;
            }
        }
    }

    /**
     * Recursive method to percolate down the specified item at the index, can be used with deleteMin
     * and buildHeap if needed.
     */
    /** PACLab: suitable */
	 private void percolateDown(int index) {
        int currentSize = Verifier.nondetInt();
		// if this index has no children then return
        if (index * 2 + 1 >= currentSize)
            return;
        // in the case of 1 child
        if (index * 2 + 1 < currentSize && index * 2 + 2 >= currentSize) {
            if (Verifier.nondetInt() > 0) {
                return;
            }
            return;
        }
        // case of 2 children
        int smallerChild;
        // determine smaller of the two children
        if (Verifier.nondetInt() <= 0)
            smallerChild = index * 2 + 1;
        else
            smallerChild = index * 2 + 2;
        // compare smaller child with item at current index
        if (Verifier.nondetInt() > 0) {
        }
        // if item is already in the correct position, just return
        return;
    }
}
