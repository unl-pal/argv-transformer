package assignment8;

import java.util.LinkedList;

/**
 * A closed-addressed HashTable implementation which uses separate chaining to resolve collisions and doesn't allow for
 * duplicate items.
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 */
public class ChainingHashTable extends HashTable {
    // underlying data structure for ChainingHashTable, uses seperate chaining to avoid collisions
    private LinkedList<String>[] storage;
    private double lambda_MAX;

    // end of class
}
