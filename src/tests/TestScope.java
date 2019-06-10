package tests;
/*
 * 3
 */
public class TestScope {

	int c1 = 1;

	public void test() {
		c1 = 5 + 5; 
		{
			double c1 = 1.0;

		}

	}

}
