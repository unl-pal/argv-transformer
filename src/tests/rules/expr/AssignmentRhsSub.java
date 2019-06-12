package tests.rules.expr;

import gov.nasa.jpf.symbc.Debug;
import tests.rules.Box;

public class AssignmentRhsSub {

	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			int y;
			y = b.getWidth();
			if(x == y) {
				
			}
		}
	}

	public static void main(String[] args) {
		test(0);
	}
}

// 0 != width
// 0 = width



