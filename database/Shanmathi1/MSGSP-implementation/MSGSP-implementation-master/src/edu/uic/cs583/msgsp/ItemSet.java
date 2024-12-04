package edu.uic.cs583.msgsp;
import java.util.ArrayList;

/*
 * This is the class used to abstract itemset.
 * Every itemset will contains several items indicated by their itemIDs.
 */
public class ItemSet {
        /*
         * Need to use ArrayList instead of HashSet
         * Order of items need to be maintained during MSCandidate-Gen
         */
        public ArrayList<Integer> items;
}

