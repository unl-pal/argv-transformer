package edu.unl.cse.instructions;

import java.util.ArrayList;
import edu.unl.cse.Compiler;
import edu.unl.cse.Constant;
import edu.unl.cse.Location;

public class Instruction {
	
	
	
	
	protected ArrayList<String> components = new ArrayList<String>();
	protected String name;
	protected String setBit ="0";
	protected String condition="1110";
	
	private enum RTypeInstruction{
		add, sub, and, or, xor, sll;
	}
}
