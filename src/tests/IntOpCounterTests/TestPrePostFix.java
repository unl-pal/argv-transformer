package tests.IntOpCounterTests;
/*
 * 17
 */
public class TestPrePostFix {
	public static void main(String[] args){
		int a = 9;
		
		System.out.println(2 + 9 +4/2 + a++ + 115/3 + 8); // 8
		
		System.out.println((9 +4/2 + (-a)/2)); // 4
		
		System.out.println((9 +4/2 + (--a)/2)); //5

		System.out.println(a);

	}

	
}
