package tests.rules.stmt;

import gov.nasa.jpf.symbc.Debug;
import tests.rules.Box;

public class MethInvocStmtRemoval {

	private static void test(int x) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if(b != null) {
			b.setWidth(5);
			int y = b.getWidth();
			if(x == y) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		test(0);
	}
}

// (1) x != width
// (2) x = width 

