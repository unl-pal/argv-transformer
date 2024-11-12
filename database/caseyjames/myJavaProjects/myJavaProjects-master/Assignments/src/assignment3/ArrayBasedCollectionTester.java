package assignment3;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

public class ArrayBasedCollectionTester extends TestCase {
    ArrayBasedCollection<String> emptyCollection, testCollectionAdd, testCollectionAddAll,
            testCollectionClear, testCollectionContainsAll, testCollectionIterator,
            testCollectionRemove, testCollectionRemoveAll, testCollectionRetainAll, testCollectionSize,
            testCollectionToArray, testCollectionGrow, testCollectionToSortedList;

    // this collection is used for testing binarySearch
    ArrayBasedCollection<Integer> testList;
}