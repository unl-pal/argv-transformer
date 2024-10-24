package testclasses;

import util.ArgVRandom;

public class SymbolicInttest {
	public static int splitter(int input1, int input2) {
		int value = (int) (ArgVRandom.randomInt() * 10);
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main (String[] args) {
		// int fake = Fake.getInt();
		int time = (int) System.nanoTime();
		int var2 = (int) (Math.random() * 5);
		System.out.println(splitter(time, var2));
	}

}
