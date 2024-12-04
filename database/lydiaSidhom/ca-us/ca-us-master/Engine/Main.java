package Engine;

import java.io.File;

public class Main {
	Initializer ourinitializer;
	InstructionsMemory IM;
	ALU alu;
	RegistersFile registers;
	Register PC;
	DataMemory DM;
	ControlUnit CU;
	ALUControlUnit ALUCU;
	SignExtender signextender;
	Shifter shifter;
	ShifterJump shiftjump;
	ALUAdder increment4,branchadder;
	MUX2to1 ALUsrc, branch;
	MUX4to1 MemtoRegister,RegDestination, JumpMux;
}
