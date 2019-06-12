package tests.rules.stmt;

import gov.nasa.jpf.symbc.Debug;
import tests.rules.Box;

public class VarDecRemoval {

	public static void test(int x, int y) {
		Box b = new Box();
		b = (Box) Debug.makeSymbolicRef("box", b);
		if (b != null) {
			int z = b.getLength();
			if (x < z) {
				if (y < z) {
				}
			}
		}
	}

	public static void main(String[] args) {
		test(1, 2);
	}
}

// (1) x >= length
// (2) y < length && x < length 
// (3) y >= length && x < length


