package tests.IntOpCounterTests;

public class TestLoopDeclarations {
	
	int inst;

	public void test(int param) {
		
		int loc;

		for(int i = 0; i<10; i++){ // 2
			
			int a,b;

			for(int j = 0; j < 10; j++) { // 2
				int c;
			}
		}

	}

}
