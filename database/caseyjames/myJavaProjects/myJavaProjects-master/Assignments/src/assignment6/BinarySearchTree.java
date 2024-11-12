package assignment6;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * A BST class in which elements are Comparable (necessary for all BSTs) and without duplicates
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type>, TreeTraversal<Type> {
    private BinaryNode root;
    private int size;

    /**
     * Represents a general binary tree node. Each binary node contains data, a left child, and a right child
     */
    private class BinaryNode {
        // Since the outer BST class declares <Type>, we can use it here without redeclaring it for BinaryNode
        private Type data;
        private BinaryNode left, right;

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
                throw new NoSuchElementException("Tried BinaryNode.remove with the invalid direction " + direction + "!");
            if (direction == - 1 && left == null)
                throw new NoSuchElementException("Tried BinaryNode.remove to the left with no left child!");
            if (direction == 1 && right == null)
                throw new NoSuchElementException("Tried BinaryNode.remove to the right with no right child!");

            // implement removal based on the direction and number of children
            if (direction == - 1) { // removing the left child
                if (left.isLeaf())
                    remove0(direction);
                else if (left.numChildren() == 1)
                    remove1(direction);
                else remove2(direction);
            } else { // removing the right child
                if (right.isLeaf())
                    remove0(direction);
                else if (right.numChildren() == 1)
                    remove1(direction);
                else remove2(direction);
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
                    left.data = left.right.data;
                    left.remove(1);
                    return;
                }
                // otherwise find the parent of the successor then copy the successor's data and remove it
                BinaryNode parentNode = left.right;
                while (parentNode.left.left != null)
                    parentNode = parentNode.left;
                left.data = parentNode.left.data;
                parentNode.remove(- 1);
            } else { // removing right node
                // if the right node has no left children then it is the successor - copy its data and remove it
                if (right.right.left == null) {
                    right.data = right.right.data;
                    right.remove(1);
                    return;
                }
                // otherwise find the parent of the successor then copy the successor's data and remove it
                BinaryNode parentNode = right.right;
                while (parentNode.left.left != null)
                    parentNode = parentNode.left;
                right.data = parentNode.left.data;
                parentNode.remove(- 1);
            }
        }
    }
}
