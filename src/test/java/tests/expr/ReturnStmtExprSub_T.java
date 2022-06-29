package tests.expr;

import gov.nasa.jpf.symbc.Debug;

public class ReturnStmtExprSub_T {
	
	private static int test() {
		int y = (int) Debug.makeSymbolicInteger("y");
		if (y <= 1200) {
		} 
		return (int) Debug.makeSymbolicInteger("y");
	}

	public static void main(String[] args) {
		test();
		Debug.printPC("Path Condition: ");
	}
}

// y <= 1200
// y > 1200