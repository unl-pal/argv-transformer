/** filtered and transformed by ARG-V */
 package transformer.integration;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int splitter(int input1, int input2) {
		int value = (int) (Verifier.nondetInt() * 10);
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
