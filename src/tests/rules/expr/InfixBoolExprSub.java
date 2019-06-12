package tests.rules.expr;

import gov.nasa.jpf.symbc.Debug;
import tests.rules.Box;

public class InfixBoolExprSub {
	
	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			int y;
			y = b.getWidth();
	        if ( x <= 100 &&  y <= 1) {
//	            Debug.printPC("Path Condition: ");
	        }
//            Debug.printPC("Path Condition: ");

		}
	}
	
	public static void main(String[] args) {
		test(0);
        Debug.printPC("Path Condition: ");
	}
}

// width <= 1
// width > 1


