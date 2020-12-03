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


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


/** filtered by PAClab */
 public class Compiler {

//There is at least function at the bottom...
	
	//WORKS, DO NOT TOUCH KTHANKS.
	public static String returnBinaryNumber(int number, int stringLength){
		String binary = "";
		if(number>0){
			binary = Integer.toBinaryString(number);
			for(int i= (stringLength-binary.length()); i>0; i--){
				binary = "0"+binary;
			}
		} else if (number<0){
			int numberTemp = ((-1*number) - 1);
			String tempBinary = Integer.toBinaryString(numberTemp);
			tempBinary = "0"+tempBinary; //this is needed for numbers of power 2
			for(int i= (stringLength-tempBinary.length()); i>0; i--){
				binary = "1"+binary;
			}
			for(int i = 0; i<tempBinary.length(); i++){
				if(tempBinary.substring(i, i+1).equals("0")){
					binary = binary.concat("1");
				} else {
					binary = binary.concat("0");
				}
			}
		} else {
			for(int i=0; i<stringLength; i++){
				binary += "0";
			}
		}
		
		return binary;
	}


}
