/** filtered and transformed by PAClab */
package edu.unl.cse;
/* 
	This file was created by
	Mathew Reny
	Mhreny@gmail.com
	
	Email me if you have any questions.
	
	I HAD TO MAKE THIS ALL IN ONE PACKAGE FOR A REASON!
	THE REASON IS THAT I WASN'T ABLE TO GET THE PACKAGE
	TO WORK WITH JAVAC. IF YOU FIGURE OUT HOW TO DO THIS
	PLEASE EMAIL ME! IT WOULD ALLOW THIS CODE TO BE SO
	MUCH MORE MANAGEABLE.
	
*/


import org.sosy_lab.sv_benchmarks.Verifier;


public class Compiler {

//There is at least function at the bottom...
	
	//WORKS, DO NOT TOUCH KTHANKS.
	/** PACLab: suitable */
	 public static Object returnBinaryNumber(int number, int stringLength){
		if(number>0){
			for(int i= (stringLength-Verifier.nondetInt()); i>0; i--){
			}
		} else if (number<0){
			int numberTemp = ((-1*number) - 1);
			for(int i= (stringLength-Verifier.nondetInt()); i>0; i--){
			}
			for(int i = 0; i<Verifier.nondetInt(); i++){
				if(Verifier.nondetBoolean()){
				} else {
				}
			}
		} else {
			for(int i=0; i<stringLength; i++){
			}
		}
		
		return new Object();
	}

}
