package tests;

public class TestLoops {
	

	void test(int a) {
		Box b = new Box();
	
		int x = 5;
		
		for(x = 0; x < b.getHeight(); x++) {
			
		}
		
		for(x = 0; b.getHeight() > x; x++) {
			
		}
		
		for(int y = 0; x < y; y--) {
			
		}
		
		for(int i = 0; b.getHeight() >= i; i--) {
			
		}
		
		while(b.getHeight() < 10) {
			while(b.getHeight() > 1 && b.getHeight() < 2.5) {
				
			}	
		}
		
	}
	
	class Box {

		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
