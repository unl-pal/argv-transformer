/** filtered and transformed by ARG-V */
 package transformer.integration;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int splitter(float input1, float input2) {
		float value = (float) (Verifier.nondetFloat() * 10.0);
		float baseCase = Verifier.nondetFloat();
		float parenthesizedTime = ((float) System.nanoTime());
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
		// float fake = Fake.getFloat();
		float time = (float) System.nanoTime();
		float var2 = (float) (Math.random() * 5);
		int var3 = splitter(time, var2);
	}

}
