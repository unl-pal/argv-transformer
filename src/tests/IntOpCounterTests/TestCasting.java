package tests.IntOpCounterTests;

public class TestCasting {
	// 14
	public void test(int a, double b) {
		
		System.out.println(a + (int) b); // 1
		System.out.println((int) (a + b)); // 1
		
		System.out.println((double) a + b); // 0
		System.out.println((double) (a + b)); // 0
		
		int c = (int)( a / (b + a - 2)); // 3

		double g =  (double) (a / (b + a - 2) + (int) c++); // 0
		
		c =  (int) (double) a * (int) b; // 1
		c = (int) 5.5 / 2; // 1
		c = (int) ( (double) 5.5 / 2); // 1
		
		double d = (float) (a / ((int) b + a - 2)); // 0
		
		double h = (int) a + (int) b; // 1
		
		double m = -5.5 + 8; // 0
		
		System.out.println((int) ((float) 1.6 - (long) 6) / (double) 2 + 'a'); // 0
		
		System.out.println((int) ((float) 1.6 - (long) 6) / (int) (double) 2 + 'a'); // 3
		
		System.out.println((double) 'a' - 9); // 0
		
		System.out.println((int) (9.5 - 'q')/15); // 2

	}
}
