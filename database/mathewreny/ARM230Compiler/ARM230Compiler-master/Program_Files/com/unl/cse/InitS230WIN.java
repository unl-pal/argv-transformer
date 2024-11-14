package com.unl.cse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class InitS230WIN {

	private ArrayList<String> argList = new ArrayList<String>();
	
	private static enum InstructionsS230{
		add, and, or, xor, addi,
		lw, sw, jr, cmp, b, bal, j, jal, li, 
		blt, beq, bne;
	}
	
}