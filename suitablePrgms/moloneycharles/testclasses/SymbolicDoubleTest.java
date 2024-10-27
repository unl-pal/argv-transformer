package testclasses;

import util.ArgVRandom;

public class SymbolicDoubleTest {
	public static int splitter(double input1, double input2) {
		double value = (double) (ArgVRandom.randomDouble() * 10.0);
		double time = (double) System.nanoTime();
		if (value > input2) {
			return -1;
		} else if (time > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main (String[] args) {
		// double fake = Fake.getDouble();
		double time = (double) System.nanoTime();
		double var2 = (ArgVRandom.randomDouble() * 5);
		System.out.println(splitter(time, var2));
	}

}
