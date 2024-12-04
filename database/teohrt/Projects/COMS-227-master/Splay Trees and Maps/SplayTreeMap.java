package edu.iastate.cs228.hw5;

import java.util.Iterator;
import java.util.ArrayList; 

/**
*
* An implementation of a map based on a splay tree.  
*
*/
public class SplayTreeMap<K extends Comparable<? super K>, V>
{
  /**
   *
   * The key-value pairs in this Map.
   *
   */
  private SplayTreeSet<MapEntry<K, V>> entrySet = new SplayTreeSet<MapEntry<K, V>>();

}