package edu.unl.cse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import edu.unl.cse.instructions.Instruction;

public class InitTrans {
	
	private ArrayList<String> argList = new ArrayList<String>();
	private HashSet<String> validAlteraInstructions = new HashSet<String>();
	private HashSet<String> supportedAlteraInstructions = new HashSet<String>();
	private enum AlteraInstruction{
		call, jmpi, ldbu, addi, stb, br, ldb, cmpgei, ldhu, andi, sth, bge, ldh, cmplti, initda, ori, stw, blt, ldw, cmpnei, flushda, xori, bne, 
		cmpeqi, ldbuio, muli, stbio, beq, ldbio, cmpgeui, ldhuio, andhi, sthio, bgeu, ldhio, cmpltui, custom, initd, orhi, stwio, bltu, ldwio,
		rdprs, flushd, xorhi, eret, roli, rol, flushp, ret, nor, mulxuu, cmpge, bret, ror, flushi, jmp, cmplt, slli, sll, wrprs, or, mulxsu, wrctl,
		cmpne, srli, srl, nextpc, callr, cmpeq, divu, div, rdctl, mul, cmpgeu, initi, trap, cmpltu, add, sync, sub, srai, sra, and, xor, mulxss;
	}
	
	//NOT STATIC BECAUSE I WANT THE HASHSET FUNCTIONALITY!;
	private enum SupportedAlteraInstruction {
		add, and, or, xor, addi,
		ldw, stw, jmp, br, blt, beq, bne, sub, sll, call, jmpi, ldbu, ldbuio;
	}
	
	
	
}
