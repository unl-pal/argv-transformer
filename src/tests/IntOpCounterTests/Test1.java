package tests.IntOpCounterTests;
// 8
public class Test1{


	public int test(int a, int b) {
	
		
		int t = Math.abs(a);
		int d;	
		d = 5 + a/b * 6; // 3
		d = 2;
		
		C cat;
		cat = new C();
		b = C.perform();
		
		
		int c = (int) a / (b + a - 2); // 3
		
		double h = (double) a / (b + a - 2); // 0
		
		int e = c++; // 1
		
		e+=1; // 1

		a = ~4; // 0
		
		double dub = 1 + Math.abs(12.2); // 0
		double f = 9.0; // 0
		f++; // 0

		double m = -5.5 + 8; // 0
		
		return 0;
	}
	
	public static class C {

		public static int perform() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
