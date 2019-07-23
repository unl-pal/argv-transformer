package transform.expr;

import gov.nasa.jpf.symbc.Debug;
import transform.Box;

public class IfStmtExprSub {
	
	private static void test(int x) {
		int y;
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {

			if(b.isEmpty()) {
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

// box[350].isEmpty != 0
// box[350].isEmpty = 0


