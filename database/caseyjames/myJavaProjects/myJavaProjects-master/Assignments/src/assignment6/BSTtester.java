package assignment6;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

public class BSTtester extends TestCase {
    // null string reference to test case of NullPointerException, both item and array
    String nullString;
    ArrayList<String> nullStrOfStrings = new ArrayList<String>();
    // non-null BinarySearchTree reference to run other various test on
    BinarySearchTree<String> BSTtestList = new BinarySearchTree<String>();
    // BinarySearchTree object used to test addAll, removeAll, containsAll etc.
    ArrayList<String> arrayListTest = new ArrayList<String>(3);
}
