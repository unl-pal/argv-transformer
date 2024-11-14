package edu.uic.cs583.msgsp;
import java.util.HashSet;

/*
 * This is the class used to abstract L, Fk, and Ck in MS-GSP
 * All of them are sets of transactions, so simply define one structure
 * to represent all of them. 
 */
public class FrequentSeq {
        public HashSet<TransactionData> sequences = new HashSet<TransactionData>();
        private Integer count;
	
}
