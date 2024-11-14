package com.unl.cse;

import java.util.ArrayList;
import java.util.List;

public class Instruction {
	
	
	
	
	protected ArrayList<String> components = new ArrayList<String>();
	protected String name;
	
	private enum RTypeInstruction{
		add, sub, and, or, xor;
	}
}
