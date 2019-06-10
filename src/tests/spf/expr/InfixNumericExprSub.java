package transform.expr;

import gov.nasa.jpf.symbc.Debug;
import transform.Box;

public class InfixNumericExprSub {
	
	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			int width = b.getWidth();

			if(x > width + 2) {

			}else {

			}
		}
	}

	public static void main(String[] args) {
		test(0);
	}
}
