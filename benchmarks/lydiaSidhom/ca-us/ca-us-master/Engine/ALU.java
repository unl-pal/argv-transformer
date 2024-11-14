package Engine;

public class ALU {
	String readInput1;
	String readInput2;
	String ALUFunction;
	String ALUResult;
	int operation;
	boolean ALUZero;

	static String AND = "0000";
	static String OR = "0001";
	static String ADD = "0010";
	static String SUBTRACT = "0110";
	static String SETONLESSTHAN = "0111";
	static String SETONLESSTHANUNSIGNED = "1000";
	static String NOR = "1100";
	static String SLL = "1010";
	static String SRL = "1001";

	public void doOperation() throws Exception {
		if (readInput1 == null || readInput2 == null
				|| readInput1.length() < 32 || readInput2.length() < 32) {
			throw new Exception("Wrong Input !!");
		}
		switch (operation) {
		case 0:
			ALUResult = Long.toBinaryString(Long.parseLong(readInput1, 2)
					& Long.parseLong(readInput2, 2));
			break;
		case 1:
			ALUResult = Long.toBinaryString(Long.parseLong(readInput1, 2)
					| Long.parseLong(readInput2, 2));
			break;
		case 2:
			ALUResult = Long
			.toBinaryString((Long.parseLong(readInput1.charAt(0)=='1'?"-"+twoComplement(readInput1):readInput1, 2) + Long
					.parseLong(readInput2.charAt(0)=='1'?"-"+twoComplement(readInput2):readInput2, 2)));
			break;
		case 3:
			ALUResult = Long
			.toBinaryString((Long.parseLong(readInput1.charAt(0)=='1'?"-"+twoComplement(readInput1):readInput1, 2) - Long
					.parseLong(readInput2.charAt(0)=='1'?"-"+twoComplement(readInput2):readInput2, 2)));
			break;
		case 4:
			ALUResult = Long
					.toBinaryString((Long.parseLong(readInput1, 2) < Long
							.parseLong(readInput2, 2)) ? 1 : 0);
			break;
		case 5:
			ALUResult = Long
					.toBinaryString((Long.parseLong(readInput1.charAt(0)=='1'?"-"+twoComplement(readInput1):readInput1, 2) < Long
							.parseLong(readInput2.charAt(0)=='1'?"-"+twoComplement(readInput2):readInput2, 2)) ? 1 : 0);
			break;
		case 6:
			ALUResult = Long.toBinaryString(~((Long.parseLong(readInput1,
					2) | Long.parseLong(readInput2, 2))));
			break;
		case 7:
			ALUResult = Long.toBinaryString(((Long.parseLong(readInput1,
					2) << Long.parseLong(readInput2, 2))));
			break;
		case 8:
			ALUResult = Long.toBinaryString(((Long.parseLong(readInput1,
					2) >> Long.parseLong(readInput2, 2))));
			break;
		default:
			System.out.print("ALU Idle");
		}
		if (ALUResult.length() > 32) {
			ALUResult = ALUResult.substring(ALUResult.length()-32);
		}
		if (ALUResult.length() < 32) {
			for(int i =ALUResult.length();i<32;i++){
				ALUResult = 0+ALUResult;
			}
		}
		if (Long.parseLong(ALUResult, 2) == 0) {
			ALUZero = true;
		} else {
			ALUZero = false;
		}
	}
}
