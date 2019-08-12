package tests.expr;

import gov.nasa.jpf.symbc.Debug;

public class IfStmtExprSub_T {
	
	private static void test(int x) {
		int y;
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			boolean a;
			a = Debug.makeSymbolicBoolean("y");
			if(a) {
				y = 0;
			} else {
				y = x;
			}
		}
	}

	public static void main(String[] args) {
		test(0);
        Debug.printPC("Path Condition: ");
	}
}

// y != 0
// y = 0
