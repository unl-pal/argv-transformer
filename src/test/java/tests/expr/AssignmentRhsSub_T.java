package tests.expr;

import gov.nasa.jpf.symbc.Debug;

public class AssignmentRhsSub_T {
	
	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			int y = Debug.makeSymbolicInteger("y");

			if(x == y) {
				
			}
		}
	}

	public static void main(String[] args) {
		test(0);
	}
}

// (1) 0 != y
// (2) 0 = y 

