package tests;

public class TestInnerClasses {

	public static void main(String[] str) {
		A a = new  A();
		int b = ((1)) + a.getX();
		
		if(3 < a.getX()) {
			if(3 < 5 && a.isTrue()) {
				
			}
		}
		// Non-Static Inner Class
		// Requires enclosing instance
		A.B obj1 = a.new B();

		// Static Inner Class
		// No need for reference of object to the outer class
		A.C obj2 = new A.C();
		
	}
}

class A {
	int a;
	class B {
		// static int x; not allowed here
	}

	static class C {
		static int x; // allowed here
	}

	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isTrue() {
		// TODO Auto-generated method stub
		return false;
	}
}
