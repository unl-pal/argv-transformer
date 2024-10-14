package testclasses;

public class MinimalSymbolicIntTestCase {
	public static int baseTestCase(int x) {
		int replaceTarget = (int) Math.random() * 10;
		if (x > replaceTarget) {
			return 1;
		} else {
			return 0;
		}
	}

}
