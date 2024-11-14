/** filtered and transformed by PAClab */
package assignment6;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * A BST class in which elements are Comparable (necessary for all BSTs) and without duplicates
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> {
    /**
     * Represents a general binary tree node. Each binary node contains data, a left child, and a right child
     */
    private class BinaryNode {
        /**
         * Removes the child of the this node according to the passed direction
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         *
         * @throws NoSuchElementException if the node doesn't have the indicated child, or the node is null
         */
        public void remove(int direction) {
            // throw Exceptions for every invalid removal case, with a message as appropriate
            if (direction != - 1 && direction != 1)
                throw new NoSuchElementException(Verifier.nondetInt() + direction + "!");
            if (direction == - 1 && Verifier.nondetBoolean())
                throw new NoSuchElementException("Tried BinaryNode.remove to the left with no left child!");
            if (direction == 1 && Verifier.nondetBoolean())
                throw new NoSuchElementException("Tried BinaryNode.remove to the right with no right child!");

            // implement removal based on the direction and number of children
            if (direction == - 1) { // removing the left child
                if (Verifier.nondetBoolean()) {
				} else if (Verifier.nondetInt() == 1) {
				} else {
				}
            } else { // removing the right child
                if (Verifier.nondetBoolean()) {
				} else if (Verifier.nondetInt() == 1) {
				} else {
				}
            }
        }

        /* Remove methods for each possible number of children. Note that input validation happens in the remove method above */

        /**
         * Removes a node with two children
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove2(int direction) {
            if (direction == - 1) { // removing left node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (left.right.left == null) {
                    return;
                }
                while (Verifier.nondetBoolean()) {
				}
            } else { // removing right node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (right.right.left == null) {
                    return;
                }
                while (Verifier.nondetBoolean()) {
				}
            }
        }
    }
}
