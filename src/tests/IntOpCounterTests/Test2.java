package tests.IntOpCounterTests;

public class Test2 {
// 11
	public void test() {
		int count = 0;
		int a = 0;

		while (count < 10) { // 1
			count++; // 1
			if (count < a) { // 1
				// assert false;
				System.out.println((a) / 8 + (16*4 - (int) (double) 3 + 19)); // 5
			}
		}

		for (int i = 0; i < 5; i++) { // 2
			System.out.println(i++); // 1
		}
	}

}
