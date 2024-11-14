package edu.unl.cse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	
	private static enum InstructionsS230{
		add, and, or, xor, addi,
		lw, sw, jr, cmp, b, bal, j, jal, li, 
		blt, beq, bne, sub, si, sll;
	}	
}
