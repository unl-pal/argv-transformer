package tests.stmt;

import gov.nasa.jpf.symbc.Debug;

public class MethInvocStmtRemoval_T {

	private static void test(int x) {
		int y = (int) Debug.makeSymbolicInteger("y");

		if (x == y) {
		}
	}

	public static void main(String[] args) {
		test(0);
	}
}

//(1) x = y
//(2) x != y
