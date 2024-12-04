package Engine;

public class ALUControlUnit {
	
	String ALUOpcode;
	String funct;
	String ResultALUFunction;
	
	static String RFORMAT 	= "000";
	static String BEQN 		= "001";
	static String SLTI 		= "010";
	static String SLTIU 	= "011";
	static String ADDI 		= "100";
	static String ANDI 		= "101";
	static String ORI 		= "110";
	static String LWSW 		= "111";
	
	static String ADDCODE 	= "010000";
	static String SUBCODE 	= "100010";
	static String SLLCODE 	= "000000";
	static String SRLCODE	= "000010";
	static String ANDCODE 	= "100100";
	static String ORCODE	= "100101";
	static String NORCODE	= "100111";
	static String SLTCODE	= "101010";
	static String SLTUCODE	= "101011";
	
	int ALUOpcodeId;
	int functId;
}
