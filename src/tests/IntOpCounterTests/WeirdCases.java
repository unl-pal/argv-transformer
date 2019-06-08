package tests.IntOpCounterTests;

public class WeirdCases {
	
	void test1(){
		int c = 0;
		int e = 0;
		e+= 3.5 + 1.5 + c++;
	}
	

	void test2() {
		int a = 5 + 1;
		if (a < 5) {
			assert false;
		}
	}

}
