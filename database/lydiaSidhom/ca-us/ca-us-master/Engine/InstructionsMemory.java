package Engine;

/*
Author : Christine.
This is the class of InstructionsMemory.... it takes two inputs the beginning address
of instructions (i.e. origin (for example org 32))
and list of binary instructions computed before...

Provides getInstructionAt(that takes a certain address and returns the instruction at this address
from the list of binary instructions.)

*/

import java.util.ArrayList;
public class InstructionsMemory {
	int beginAddress;
	ArrayList<String> binaryInstructions=new ArrayList<String>();
}
