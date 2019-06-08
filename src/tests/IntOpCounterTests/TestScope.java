package tests.IntOpCounterTests;
/*
 * 3
 */
public class TestScope {

	int c1 = 1;

	public void test() {
		c1 = 5 + 5; // 1
		{
			char c1 = 1;
			System.out.println(c1 + 2 - 1); // 0

		}

		System.out.println(c1 + 5 - 10); // 2
	}

}
