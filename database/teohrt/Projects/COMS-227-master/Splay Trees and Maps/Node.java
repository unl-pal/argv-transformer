package edu.iastate.cs228.hw5;

/**
 * 
 * @author Caleb Brose
 *
 */

/**
 * 
 * This class represents a tree node. This class is fully implemented and should not 
 * be modified.
 * 
 */

public class Node<E extends Comparable<? super E>> {
    private E data;
    private Node<E> parent;
    private Node<E> left;
    private Node<E> right;
}
