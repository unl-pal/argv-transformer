package transform.expr;

import gov.nasa.jpf.symbc.Debug;
import transform.Box;

public class InfixNumericExprSub_T {
	
	
	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			int width = (int) Debug.makeSymbolicInteger("width");

			if(x > width + 2) {

			}else {

			}
		}
	}

	public static void main(String[] args) {
		test(0);
	}
}
// 0 <= (y + 2)
// 0 > (y + 2)

