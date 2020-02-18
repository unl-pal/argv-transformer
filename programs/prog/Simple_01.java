package prog;

public class Simple_01 {
	
	public int m1(int x, int y) {
		int z = x + y;
		//boolean b = x > 2;
		if(x - 2  > Math.abs(x) && x - 7 > 0) {
		//if(b && !b) {
			z = Math.abs(x) - z;
		} else {
			z = Math.abs(z) * 2;
		}
			
		z = (x-- - 12)/y;
		return ++z + x;
	}

}
