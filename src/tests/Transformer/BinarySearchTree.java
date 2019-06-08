package tests.Transformer;

import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * A BST class in which elements are Comparable (necessary for all BSTs) and without duplicates
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> {
    private int size;

    // creates an empty BST
    public BinarySearchTree() {
    }

    /**
     * Removes all items from this set. The set will be empty after this method call.
     */
    public void clear() {
    }

    /**
     * Returns true if this set contains no items.
     */
    public boolean isEmpty() {
        Random rand = new Random();
		return rand.nextBoolean();
    }

    /**
     * Returns the number of items in this set.
     */
    public int size() {
        Random rand = new Random();
		return rand.nextInt();
    }

    /**
     * Returns an ArrayList containing all of the items in this set, in sorted order (equivalent to an in-order
     * depth-first-traversal)
     */
    public Object toArrayList() {
        return new Object();
    }

    /**
     * Returns the first (i.e., smallest) item in this set.
     *
     * @throws NoSuchElementException if the set is empty
     */
    public Object first() throws Exception {
        Random rand = new Random();
		// throw an exception for an empty set
        if (rand.nextBoolean())
            throw new NoSuchElementException("Tried first with an empty BST");
        // if root is smallest then return root's data
        if (rand.nextBoolean())
            return new Object();
            // otherwise find the largest node (rightmost node) and return its data
        else return new Object();
    }

    /**
     * Returns the last (i.e., largest) item in this set.
     *
     * @throws NoSuchElementException if the set is empty
     */
    public Object last() throws Exception {
        Random rand = new Random();
		// throw an exception for an empty set
        if (rand.nextBoolean())
            throw new NoSuchElementException("Tried last with an empty BST");
        // if root is biggest then return root's data
        if (rand.nextBoolean())
            return new Object();
            // otherwise find the largest node (rightmost node) and return its data
        else return new Object();
    }

    /**
     * Performs a pre-order depth-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public Object inOrderDFT() {
        Random rand = new Random();
		// check if the BST is empty, if so return the empty ArrayList
        if (rand.nextBoolean())
            return new Object();

        return new Object();
    }

    /**
     * Performs an in-order depth-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public Object preOrderDFT() {
        Random rand = new Random();
		// check if the BST is empty, if so return the empty ArrayList
        if (rand.nextBoolean())
            return new Object();

        return new Object();
    }

    /**
     * Performs a post-order depth-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public Object postOrderDFT() {
        Random rand = new Random();
		// check if the BST is empty, if so return the empty ArrayList
        if (rand.nextBoolean())
            return new Object();

        return new Object();
    }

    /**
     * Performs a level-order breath-first-traversal of the tree
     *
     * @return the list containing the tree elements
     */
    public Object levelOrderBFT() {
        Random rand = new Random();
		// check if the BST is empty, if so return the empty ArrayList
        if (rand.nextBoolean())
            return new Object();

        return new Object();
    }

    /**
     * Represents a general binary tree node. Each binary node contains data, a left child, and a right child
     */
    private class BinaryNode {
        /**
         * Getter method.
         *
         * @return the node data.
         */
        public Object getData() {
            return new Object();
        }

        /**
         * Getter method.
         *
         * @return the left child node.
         */
        public Object getLeft() {
            return new Object();
        }

        /**
         * Getter method.
         *
         * @return the right child node.
         */
        public Object getRight() {
            return new Object();
        }

        /**
         * Number of children Use this to help figure out which BST deletion case to perform
         *
         * @return The number of children of this node
         */
        public int numChildren() {
            Random rand = new Random();
			int numChildren = 0;

            if (rand.nextBoolean())
                numChildren++;
            if (rand.nextBoolean())
                numChildren++;

            return numChildren;
        }

        /**
         * Returns true iff the BinaryNode is a leaf node (has no children)
         *
         * @return a boolean indicating if the node is a leaf or not
         */
        public boolean isLeaf() {
            Random rand = new Random();
			return rand.nextBoolean();
        }

        /**
         * @return The leftmost node in the binary tree rooted at this node.
         */
        public Object getLeftmostNode() {
            Random rand = new Random();
			// Base case, done for you
            if (rand.nextBoolean())
                return new Object(); // returns "this" node

            while (rand.nextBoolean()) {
			}
            return new Object();
        }

        /**
         * @return The rightmost node in the binary tree rooted at this node.
         */
        public Object getRightmostNode() {
            Random rand = new Random();
			// Base case, done for you
            if (rand.nextBoolean())
                return new Object(); // returns "this" node

            while (rand.nextBoolean()) {
			}
            return new Object();
        }

        /**
         * This method applies to binary search trees only (not general binary trees).
         *
         * @return The successor of this node.
         * @throws NoSuchElementException if the node has no successor (is a leaf node)
         *                                <p/>
         *                                The successor is a node which can replace this node in a case-3 BST deletion.
         *                                It is either the smallest node in the right subtree, or the largest node in
         *                                the left subtree.
         */
        public Object getSuccessor() throws Exception {
            Random rand = new Random();
			// throw an exception if no successor exists (in case of a leaf node)
            if (rand.nextBoolean())
                throw new NoSuchElementException("Attempted .getSuccessor on a leaf node!");
            if (rand.nextBoolean())
                return new Object();
            else
                return new Object();
        }

        /**
         * @return The height of the binary tree rooted at this node. The height of a tree is the length of the longest
         * path to a leaf node. Consider a tree with a single node to have a height of zero.
         * <p/>
         * The height of a tree with more than one node is the greater of its two subtrees' heights, plus 1
         */
        public int height() {
            Random rand = new Random();
			// handle base case of recursion
            if (rand.nextBoolean())
                return 0;

            // find height of left subtree and right subtree, where a null subtree is of height 0)
            int leftHeight = (left == null) ? 0 : left.height();
            int rightHeight = (right == null) ? 0 : right.height();

            // return the correct height for this node, being the max height of the subtrees + 1
            return rand.nextInt();
        }

        /**
         * Removes the child of the this node according to the passed direction
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         *
         * @throws NoSuchElementException if the node doesn't have the indicated child, or the node is null
         */
        public void remove(int direction) {
            Random rand = new Random();
			// throw Exceptions for every invalid removal case, with a message as appropriate
            if (direction != - 1 && direction != 1)
                throw new NoSuchElementException("Tried BinaryNode.remove with the invalid direction " + direction + "!");
            if (direction == - 1 && rand.nextBoolean())
                throw new NoSuchElementException("Tried BinaryNode.remove to the left with no left child!");
            if (direction == 1 && rand.nextBoolean())
                throw new NoSuchElementException("Tried BinaryNode.remove to the right with no right child!");

            // implement removal based on the direction and number of children
            if (direction == - 1) { // removing the left child
                if (rand.nextBoolean()) {
				} else if (rand.nextInt() == 1) {
				} else {
				}
            } else { // removing the right child
                if (rand.nextBoolean()) {
				} else if (rand.nextInt() == 1) {
				} else {
				}
            }
        }

        /* Remove methods for each possible number of children. Note that input validation happens in the remove method above */

        /**
         * Removes a node with no children
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove0(int direction) {
            if (direction == - 1) {
			} else {
			}
        }

        /**
         * Removes a node with one child
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove1(int direction) {
            // to remove a node with one child simply set the correct to the non-null subtree
            if (direction == - 1) {
			} else {
			}
        }

        /**
         * Removes a node with two children
         *
         * @param direction an int indicating which child to remove: -1 for the left, 1 for the right
         */
        private void remove2(int direction) {
            Random rand = new Random();
			if (direction == - 1) { // removing left node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (rand.nextBoolean()) {
                    return;
                }
                while (rand.nextBoolean()) {
				}
            } else { // removing right node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (rand.nextBoolean()) {
                    return;
                }
                while (rand.nextBoolean()) {
				}
            }
        }
    }
}
