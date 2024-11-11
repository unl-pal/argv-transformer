/** filtered and transformed by ARG-V */
 package testclasses;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicInttest {
	/** ARG-V: suitable */
	 public static int splitter(int input1, int input2) {
		int value = (int) (Debug.makeSymbolicInteger("x18") * 10);
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/** ARG-V: suitable */
	 public static void main (String[] args) {
		// int fake = Fake.getInt();
		int time = (int) System.nanoTime();
		int var2 = (int) (Math.random() * 5);
	}

}
