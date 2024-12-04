/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uic.cs583.msgsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
/*
 * Created by: Stuti and Shanmathi
 * Date: Feb 2, 2015
 * Description: Implementation oF MS-GSP
 */

public class MSGSPMain {
	/*	Algorithm MS-GSP(S, MS)                                           // MS stores all MIS values
	1  M <-sort(I, MS);												 // according to MIS(i)’s stored in MS
	2  L <- init-pass(M, S);											// make the first pass over S
	3  F_1 <- {<{l}> | l  L, l.count/n >= MIS(l)}; 				   // n is the size of S
	4  for (k = 2; F k-1 != SDC; k++) do
	5  if k = 2 then
	6  C_k <- level2-candidate-gen-SPM(L)
	7  else C_k <- MScandidate-gen-SPM(F k_1 )
	8  endif
	9  for each data sequence s  S do
	10 for each candidate c  C k do
	11 if c is contained in s then
	12 c.count++
	13 if c’ is contained in s, where c’ is c after an occurrence of
	   c.minMISItem is removed from c then
	14 c.rest.count++										// c.rest: c without c.minMISItem
	15 endfor
	16 endfor
	17 F_k <- {c <- C_k | c.count/n >= MIS(c.minMISItem)}
	18 endfor
	19 return F <- U_k F_k ;*/

        // Each element in MS is a pair of itemID and its corresponding minimum support
        public static HashMap<Integer,Float> MS;  
        /*
         * S is the an array of transactions which is abstracted as TransactionData class.  
         * Each transaction is an array of itemsets which is abstracted as ItemSet class.  
         */
        public ArrayList<TransactionData> S; 
        /*
         * N is the number of transactions in S
         */
        public static int N;
        /*
         * SDC is support distance constraint
         */
        public static double SDC;
        /*
         * SUP stores the support count for each item using a HashMap
         */
        public static HashMap<Integer, Integer> SUP = new HashMap<Integer, Integer>();
        
        
        public void msgsp(){
            
                
                LinkedList<Integer> M=sort(MS); // according to MIS(i)'s stored in MS
           

               
                ArrayList<Integer> L= initPass(M);   // make the first over S
                
                ArrayList<FrequentSeq> F=new ArrayList<FrequentSeq>(); //F1 U F2 U F3....U Fk 
            // In order to make the index here synchronized with that in the book(start from 1), let F0 be a empty FreqSequence  
                F.add(new FrequentSeq()); 
                
                
                F.add(initPrune(L));    //obtain F1 from L
                
                MSCandidateGen gen = new MSCandidateGen(); // Define a new instance of MSCandidateGen class
                Level2CandidateGen gen1 = new Level2CandidateGen();
                FrequentSeq Fk_1;
                for(int k=2; !(Fk_1=F.get(k-1)).sequences.isEmpty(); k++){
                        FrequentSeq Ck;
                        if(k==2)
                                Ck= gen1.level2CandidateGen(L); 
                        else
                                Ck=gen.candidateGen(Fk_1);

                                
                    for(TransactionData s: S)
                        for(TransactionData c: Ck.sequences){
                                if( c.containedIn(s))
                                        c.count++;
                                
                        }
                    
                    FrequentSeq Fk=new FrequentSeq();

                    for(TransactionData c: Ck.sequences) {
                        
                        if(c.count>=MS.get(c.getItems().get(c.minMISItem()))*N){
                           
                                Fk.sequences.add(c);
                                Fk.setCount(c.count);

                        }
                                
                                
                    }

                    F.add(Fk);
                                                 

                }
                F.remove(F.size()-1);
                

                
               
                // Print F
                int k=0;
                while(++k<F.size()){
                        FrequentSeq Fk=F.get(k);
                        System.out.println(k+"-Sequence");
                        System.out.println();
                        for(TransactionData tran: Fk.sequences) {
                            tran.print();
                            //System.out.println(tran.count);
                        }
                        
                        System.out.println();
                        System.out.println("The total number of "+k+"-Sequence = "+Fk.sequences.size());
                        System.out.println();
                }

                      
        }
        
        //public void sort(HashMap)
        
       

}