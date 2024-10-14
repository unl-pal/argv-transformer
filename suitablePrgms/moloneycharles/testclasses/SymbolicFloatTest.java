package testclasses;

public class SymbolicFloatTest {
	public static int splitter(float input1, float input2) {
		float value = (float) (Math.random() * 10);
		if (value > input2) {
			return -1;
		} else if (input2 > input1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static void main (String[] args) {
		float fake = Fake.getFloat();
		float time = (float) System.nanoTime();
		float var2 = (float) (Math.random() * 5);
		System.out.println(splitter(time, var2));
	}

}
