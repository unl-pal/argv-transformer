package edu.unl.cse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.unl.cse.instructions.ADD;
import edu.unl.cse.instructions.ADDI;
import edu.unl.cse.instructions.AND;
import edu.unl.cse.instructions.B;
import edu.unl.cse.instructions.BAL;
import edu.unl.cse.instructions.BEQ;
import edu.unl.cse.instructions.BLT;
import edu.unl.cse.instructions.BNE;
import edu.unl.cse.instructions.CMP;
import edu.unl.cse.instructions.Instruction;
import edu.unl.cse.instructions.J;
import edu.unl.cse.instructions.JAL;
import edu.unl.cse.instructions.JR;
import edu.unl.cse.instructions.LI;
import edu.unl.cse.instructions.LW;
import edu.unl.cse.instructions.OR;
import edu.unl.cse.instructions.SI;
import edu.unl.cse.instructions.SLL;
import edu.unl.cse.instructions.SUB;
import edu.unl.cse.instructions.SW;
import edu.unl.cse.instructions.XOR;

public class InitS230 {

	private ArrayList<String> argList = new ArrayList<String>();
	
	//Get the scanner object. Chose to make this not static 
	//because it is a global thing that needs to be reset often.
	public Scanner getScanner(){
	  if(Compiler.isWindows()){ //IF THE OPPERATING SYSTEM IS WINDOWS
		String filename = "";
		for(int i = 0; i<argList.size(); i++ ){
			if(argList.get(i).equals("-f")){
				boolean useProvidedFilename = false;
				for(String s: argList){
					if(s.equals("-S")){
						useProvidedFilename = true;
					}
				}
				if(useProvidedFilename){
					filename = argList.get(i+1);
					//DIFFERENT FROM OTHER INIT FILE!
					filename = filename.trim().replace(".s230", ".S230");
				} else {
					filename = argList.get(i+1);
					filename = filename.substring(0,filename.indexOf(".")) + "GENERATED"+".S230";
				}
				
				
			}
		}
//		System.out.println(System.getProperty("user.dir") + " ... " + filename);
		String dirName = System.getProperty("user.dir");
		dirName = dirName.replace("\\Program_Files\\source","\\").trim();
		dirName = dirName.replace("\\Program_Files\\classes","\\").trim();
		dirName = dirName.concat("\\InputOutputFolder\\");
//		System.out.println(dirName+filename);
		File file = new File(dirName, filename);
		try {
			return new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	  } else {  //IF THE OPPERATING SYSTEM IS LINUX OR UNIX
		String filename = "";
		for(int i = 0; i<argList.size(); i++ ){
			if(argList.get(i).equals("-f")){
				filename = argList.get(i+1);
				//DIFFERENT FROM OTHER INIT FILE!
				filename = filename.trim().replace(".s230", ".S230");
			}
		}
//		System.out.println(System.getProperty("user.dir") + " ... " + filename);
		String dirName = System.getProperty("user.dir");
		dirName = dirName.replace("/Program_Files/source","/").trim();
		dirName = dirName.replace("/Program_Files/classes","/").trim();
		dirName = dirName.replace("/Program_Files","/").trim();
		dirName = dirName.concat("/InputOutputFolder/");
		File file = new File(dirName, filename);
		try {
			return new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  return null;
	}
	
	
	
	
	private static enum InstructionsS230{
		add, and, or, xor, addi,
		lw, sw, jr, cmp, b, bal, j, jal, li, 
		blt, beq, bne, sub, si, sll;
	}	
}
