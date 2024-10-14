package testclasses;

public class SymbolicDoubleTest {
	public static int splitter(double input1, double input2) {
		double value = (Math.random() * 10.0d);
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main (String[] args) {
		// double fake = Fake.getDouble();
		double time = (double) System.nanoTime();
		double var2 = (Math.random() * 5);
		System.out.println(splitter(time, var2));
	}

}
