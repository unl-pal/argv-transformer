package transform.expr;

import gov.nasa.jpf.symbc.Debug;
import transform.Box;

public class InfixBoolExprSub_T {
	
	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			int y;
			y = (int) Debug.makeSymbolicInteger("y");
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

// (1) y <= 1
// (2) y > 1
