/** filtered and transformed by ARG-V */
 package transformer.integration;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int splitter(double input1, double input2) {
		double value = (double) (Verifier.nondetDouble() * 10.0);
		double time = (double) System.nanoTime();
		if (value > input2) {
			return -1;
		} else if (time > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/** ARG-V: suitable */
	 public static void main (String[] args) {
		// double fake = Fake.getDouble();
		double time = (double) System.nanoTime();
		double var2 = (Verifier.nondetDouble() * 5);
	}

}
