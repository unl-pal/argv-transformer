package tests;

public class TestCasting {
	
	public void test(int a, double b) {
		
		int c = (int)( a / (b + a - 2)); 

		double g =  (double) (a / (b + a - 2) + (int) c++);
		
		c =  (int) (double) a * (int) b; 
		c = (int) 5.5 / 2; 
		c = (int) ( (double) 5.5 / 2); 
		
		double d = (float) (a / ((int) b + a - 2)); 
		
		double h = (int) a + (int) b; 
		
		double m = -5.5 + 8;
		
		int x = (int) ((int) ((float) 1.6 - (long) 6) / (double) 2 + 'a'); 
		
		double y = (float) (1.6 - (long) 6) / (int) (double) 2 + 'a';
		
		
		x = (int) (9.5 - 'q')/15; 

	}
}
