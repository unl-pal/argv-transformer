package edu.iastate.cs228.hw4;

/**
 *  
 * @author Steve Kautz 
 *
 */


import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Sample implementation of a stack using an expandable array as the
 * backing data structure.  Elements are added and removed at the
 * end of the array.
 */
public class ArrayBasedStack<E> implements PureStack<E>
{
  private static final int DEFAULT_SIZE = 10;
  
  /**
   * Index of next available cell in array.
   */
  private int top;
  
  /**
   * The data store.
   */
  private E[] data;

}
