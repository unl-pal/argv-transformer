/** filtered and transformed by ARG-V */
 package transformer.regression;

import gov.nasa.jpf.symbc.Debug;

public class SymbolicFloatTest {
	/** ARG-V: suitable */
	 public static int splitter(float input1, float input2) {
		float value = (float) ((float) Debug.makeSymbolicReal("x12") * 10.0);
		float baseCase = (float) Debug.makeSymbolicReal("x13");
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
