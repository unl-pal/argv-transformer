import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
  * HashDictionaryImplementation
  * Justin Lim
  * 06/01/14 (Modified 06/06/14)
  * This program contains the constructor and methods of a Hash Dictionary Implementation.
  */
  
public class HashDictionaryImplementation<K, V> implements Dictionary<K, V> {
	private Object[] hashTable;
	private int num_entries;
	private int table_size;
	private static final int DEFAULT_SIZE = 97;
	/** Augmented with a final entry computed by Wolfram Alpha. */
	private static final int[] PRIMES = {
    13, 23, 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157,
    98317, 196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917,
    25165843, 50331653, 100663319, 201326611, 402653189, 805306457,
    1610612741 };
	private static final double MAX_LOAD_FACTOR = 0.5;
	
	/** Node class that keeps track of both the key and the value of a data entry
	  * and the node connected to it if there are multiple entries with the same
	  * key.
	  */	
	private class Node {
		public K key;
		public V value;
		public Node next_node;
	}
	
	/** Set class that uses the Java HashSet Class. Some methods are not contained
	  * in HashSet so exceptions are thrown for them in the JavaSetWrapper class.
	  */
	private class JavaSetWrapper<T> implements Set<T> {
		private HashSet<T> set;
	}
	
	/** Class that stores the key and value of a specific data entry. */
	private class KVPair<K, V> implements DictPair<K, V> {
		private K key;
		private V value;
	}
	
	/** Approximately doubles the size of the hashTable to the nearest prime number
	  * when the Low Load Factor gets larger than a specified value (0.5 in this case).
	  */
	private void rehash() {
		Object[] oldTable = hashTable;
		int old_size = num_entries;
		int new_size = old_size;
		int index = 0;
		while (new_size <= (2 * old_size)) {
			if (new_size < PRIMES[index]) {
				new_size = PRIMES[index];
			}
			index++;
		}
		
		@SuppressWarnings("unchecked")
  		Object[] tmp = (Object[]) new Object[new_size];
		hashTable = tmp;
		num_entries = 0;
		table_size = new_size;
		
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != null) {
				@SuppressWarnings("unchecked")
       			Node current_node = (Node) oldTable[i];
       			add(current_node.getKey(), current_node.getValue());
       		}
       	}
	}
}
