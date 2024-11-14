/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/** filtered and transformed by PAClab */
package edu.uic.cs583.msgsp;

import org.sosy_lab.sv_benchmarks.Verifier;

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

        /** PACLab: suitable */
		 public void msgsp(){
            
                
                for(int k=2; !(Fk_1=F.get(k-1)).sequences.isEmpty(); k++){
                        if(k==2) {
						} else {
						}
                                                 

                }
                // Print F
                int k=0;
                while(++k<Verifier.nondetInt()){
                }

                      
        }
        
        //public void sort(HashMap)
        
       

}