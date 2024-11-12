package prog;

import java.util.HashSet;
import java.util.Set;

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
	
	
	public int m2(int x, int y) {
		int z = x + y;
		Set<Object> s = new HashSet<Object>();
		//boolean b = x > 2;
		if(x - 2  > Math.abs(x) && x - 7 > 0) {
		//if(b && !b) {
			z = Math.abs(x) - z;
		} else if(s.isEmpty()){
			z = Math.abs(z) * 2;
		}
			
		z = (x-- - 12)/y;
		return ++z + x;
	}

}
