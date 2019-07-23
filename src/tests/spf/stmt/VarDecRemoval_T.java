package transform.stmt;

import gov.nasa.jpf.symbc.Debug;

public class VarDecRemoval_T {
	
	public static void test(int x, int y) {
		int z = (int) Debug.makeSymbolicInteger("z");
		if (x < z) {
			if (y < z) {
			}
		}
//		System.out.println("z "+Debug.getSymbolicIntegerValue(z));
//		Debug.printPC("PC ");
	}

	public static void main(String[] args) {
		test(0,0);
	}
}

// (1) x >= z
// (2) y >= z && x < z
// (3) y < z && x < z
