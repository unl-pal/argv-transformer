package testclasses;

public class ComplexSymbolicIntTestCase {
	public static int variedTestCase(int x, int y, int z) {
		int v1 = (int) (Math.random() * 10);
		int v4 = (int) (Math.random() * 10) + y + helperFunctionOne(z);
		if (x > v1) {
			return helperFunctionOne(y);
		} else {
			return helperFunctionOne(v4);
		}
	}
	
	public static int helperFunctionOne(int a) {
		int v2 = (int) (Math.random() * 10);
		int v3 = (int) System.nanoTime() / 1000;
		if (a > v2 && a > v3) {
			return 1;
		} else if (a == v2 || a == v3) {
			return 2;
		} else {
			return a * 500;
		}
	}

}
