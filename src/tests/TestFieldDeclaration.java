package tests;

public class TestFieldDeclaration {
	int b = 0;
	double c;
	boolean e;
	Dog g;
	
	public void testInt() {
		int a = b + 1;
	}
	
	
	public void testFloat() {
		double d;
		d = c + 1.0;
	}
	
	public void testBool(boolean f) {
		f = e;
	}
	
	public void testOther() {
		int x = g.getHeight();
	}
	
	public class Dog {

		public int getHeight() {
			return 0;
		}
		
	}

}
