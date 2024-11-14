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
	
	//constructor
	public InitS230(String[] args){
		for(int i = 0; i<args.length; i++){
			String arg = args[i];
			this.argList.add(arg);
			//System.out.println("added "+arg);
		}
		
	}
	
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
	
	
	
	
	//check to see if valid instruction
	public static boolean isInstruction(String line){
		String[] validInstructions = {"add", "and", "or", "xor", "addi",
				"lw", "sw", "jr", "cmp", "b", "bal", "j", "jal", "li", 
				"blt", "beq", "bne", "sub", "si", "sll"};
		if(line.trim().indexOf(" ") == -1)
			return false;
		String inst2bedet = line.trim().substring(0, line.indexOf(" "));
		for(int i = 0; i<validInstructions.length; i++){
				if(validInstructions[i].equals(inst2bedet)){
					return true;
				}
		}
		return false;
	}
	
	//see if it is a location.
	public static boolean isLocation(String line){
		
		String tempLine = line.replaceAll("\\s+", "");
		tempLine = tempLine.replace("\\-\\-*", "");
		//tempLine = tempLine.replace("--*", "");
		tempLine = tempLine.trim();
		if(tempLine.length()>0 && tempLine.substring(tempLine.length()-1).equals(":")){ //DO NOT GET RID OF THE SHORT CIRCUIT
			return true;
		}
		return false;
	}
	
	
	//This is needed for the getInstruction command.
	private static ArrayList<String> sanatizeDataInstr(String lineInput){
		String line = lineInput;
		line = line.trim();
		//System.out.println("Line starts out as:("+line+")");
		ArrayList<String> toReturn = new ArrayList<String>();
		
		while(line.length()>0){
			if(line.indexOf(" ") != -1){
				toReturn.add(line.trim().substring(0, line.indexOf(" ")).trim().toLowerCase());
				line = line.substring(line.indexOf(" "), line.length()).trim();
			} else {
				toReturn.add(line.trim().toLowerCase());
				line = "";
			}
			//System.out.println("Line now equals:(" + line +")");
		}
		
		return toReturn;
	}
	
	//This is needed to create the instructions in getInstruction
	private static String getAdReg(String compon){
		//if this or sw or lw works with a constant...
		if(compon.indexOf("(") == -1){
//			System.out.println("compon set to r0");
			return "r0";
		}
		compon = compon.substring(compon.indexOf("(")+1,compon.indexOf(")"));
//		System.out.println(compon);
		return compon;
	}
	
	//This is also needed to create the instructions in getInstruction
	private static String getAdImmd(String compon){
		if(compon.indexOf("(") == -1){
//			System.out.println("Nextcompon set to "+compon.trim());
			return compon.trim();
		}
		compon = compon.substring(0,compon.indexOf("("));
//		System.out.println("index was > -1 so compon = "+compon);
		return compon;
	}
	
	//get the instruction
	private static enum InstructionsS230{
		add, and, or, xor, addi,
		lw, sw, jr, cmp, b, bal, j, jal, li, 
		blt, beq, bne, sub, si, sll;
	}
	
	
	public static Instruction getInstruction(String line){
		ArrayList<String> comps = new ArrayList<String>();
		comps = sanatizeDataInstr(line);
		
		Instruction toReturn = new Instruction();
		InstructionsS230 instr = InstructionsS230.valueOf(comps.get(0));
		//now this is a long switch lol;
		switch(instr) {
			case add:
					toReturn = new ADD(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case and:
					toReturn = new AND(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case or:
					toReturn = new OR(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case xor:
					toReturn = new XOR(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case addi:
					toReturn = new ADDI(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case sll:
					toReturn = new SLL(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case lw:
					
					toReturn = new LW(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(getAdReg(comps.get(2)));
					toReturn.setNextComponent(getAdImmd(comps.get(2)));
					break;
			case sw:
					toReturn = new SW(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(getAdReg(comps.get(2)));
					toReturn.setNextComponent(getAdImmd(comps.get(2)));
					break;
			case jr:
					toReturn = new JR(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case cmp:
					toReturn = new CMP(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					break;
			case b:
					toReturn = new B(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case bal:
					toReturn = new BAL(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case j:
					toReturn = new J(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case jal:
					toReturn = new JAL(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case li:
					toReturn = new LI(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					break;
			case blt:
					toReturn = new BLT(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case beq: 
					toReturn = new BEQ(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case bne:
					toReturn = new BNE(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					break;
			case sub:
					toReturn = new SUB(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					toReturn.setNextComponent(comps.get(3));
					break;
			case si:
					toReturn = new SI(comps.get(0));
					toReturn.setNextComponent(comps.get(1));
					toReturn.setNextComponent(comps.get(2));
					break;
		}
		
		//comps and set bit has hard coded condition flag
		for(String s: comps){
			//CHECK FOR CONDITION FLAG
			String condition = s;
			if(condition.trim().length() == 11){
				boolean isvalidcondition = true;
				condition = condition.trim();
				if(condition.contains("(cond:")){
					condition = condition.substring(6,10);
					for(char c: condition.toCharArray()){
						if(!((c=='0')||(c=='1'))){
							isvalidcondition = false;
						}
					}
					System.out.println("GOT THIS FAR"+isvalidcondition);
					if (isvalidcondition){
						toReturn.setCondition(condition);
						toReturn.setNextComponent("(COND:"+condition+")");
					}
				}
			}
			//CHECK FOR SET BIT
			if(s.equals("(setbit)")){
				toReturn.setBit(true);
				toReturn.setNextComponent("(SETBIT)");
			}
		}
		return toReturn;
	}
	
	
	
	
	public static Location getLocation(String line, int S230MemoryAddress) {
		// TODO Auto-generated method stub
		String tempLine = line.replaceAll("\\s+", "");
		tempLine = tempLine.replace(":", "");
		tempLine = tempLine.replace("\\-\\-*", "");
		tempLine = tempLine.trim();
		Location toReturn = new Location(tempLine,S230MemoryAddress);
		return toReturn;
	}

	public static Constant getConstant(String line) {
		int value = Integer.parseInt(line.substring(line.indexOf("(")+1, line.indexOf(")")));
//		System.out.println("....."+value);
		String name = line.substring(line.indexOf(".")+1, line.indexOf("("));
		return new Constant(name, value);
	}	
}
