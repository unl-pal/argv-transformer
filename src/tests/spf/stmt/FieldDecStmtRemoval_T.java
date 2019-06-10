package transform.stmt;

import java.util.Random;

import gov.nasa.jpf.symbc.Debug;

public class FieldDecStmtRemoval_T {

	public static void test(int x) {

		int field2 = (int) Debug.makeSymbolicInteger("field2");
		int field1 = (int) Debug.makeSymbolicInteger("field1");

		x = field1 + field2;

		if (x > 0) {
			x = 1;
		} else {
			x = x - field1;
		}
	}

	public static void main(String[] args) {
		test(0);
	}
}
// (1) (field2 + field1) > 0
// (2) (field2 + field1) <= 0