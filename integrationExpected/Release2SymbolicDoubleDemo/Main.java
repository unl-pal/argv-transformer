/** filtered and transformed by ARG-V */
 package transformer.integration;

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {
	/** ARG-V: suitable */
	 public static int demoMethod(int x, int y) {
		double var1 = Verifier.nondetDouble();
		double var2 = 10.0 * helperFunction(x, y);
		double result = var1 * var2;
		int z = x * y;
		if (result > z) {
			return x;
		} else {
			return y;
		}
	}
	
	/** ARG-V: suitable */
	 private static double helperFunction(int x, int y) {
		if (x > y) {
			return Verifier.nondetDouble();
		} else {
			return 10.0;
		}
	}
}
