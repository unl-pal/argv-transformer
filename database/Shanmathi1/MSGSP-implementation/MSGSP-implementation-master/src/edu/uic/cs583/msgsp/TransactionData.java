package edu.uic.cs583.msgsp;
import java.util.ArrayList;
import java.util.HashSet;

/*
 * This is the class used to abstract transaction.
 * Every transaction will contains a list of itemsets.
 */
public class TransactionData {
        public ArrayList<ItemSet> itemSets;
        public int count;  // count the occurrence of the transaction
}
