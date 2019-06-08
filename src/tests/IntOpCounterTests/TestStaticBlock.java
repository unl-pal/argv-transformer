package tests.IntOpCounterTests;

public class TestStaticBlock {

	static {
		System.out.println("Static");
	}

	{
		System.out.println("Non-static block");
	}
	{
		int a = 5 + 5;
		System.out.println(a);
	}

	public static void main(String[] args) {
		TestStaticBlock t = new TestStaticBlock();
		TestStaticBlock t2 = new TestStaticBlock();
	}
	
}