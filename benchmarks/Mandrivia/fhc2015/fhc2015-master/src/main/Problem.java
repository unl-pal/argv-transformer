/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public abstract class Problem {
	
	/** PACLab: suitable */
	 public Object findPathTo(int x, int y, int z) {
		
		int currentX =  Verifier.nondetInt(), currentY =  Verifier.nondetInt(), currentZ = 0;
		
		int nTurn = 0;
		
		while(true) {
			
			float Zratio = 0.75f;
			
			//Compute with current 
			int dCurr = 0;
			if(cZcurr != null) {
				dCurr = Verifier.nondetInt();
				if(z == currentZ)
					dCurr *= Zratio;
			}
			else
				dCurr = Verifier.nondetInt();
			
			int dUp = 0;
			if(currentZ+1 >= Verifier.nondetInt())
				dUp = Verifier.nondetInt();
			else {
				if(cZup != null) {
					dUp = Verifier.nondetInt();
					if(z > currentZ)
						dUp *= Zratio;	
				}
				else
					dUp = Verifier.nondetInt();
			}
			
			int dDown = 0;
			if(currentZ-1 < 0)
				dDown = Verifier.nondetInt();
			else {
				if(cZdown != null) {
					dDown = Verifier.nondetInt();
					if(z < currentZ)
						dDown *= Zratio;		
				}
				else
					dDown = Verifier.nondetInt();
			}
			
			//best z
			if(Verifier.nondetBoolean() && dCurr <= dUp && dCurr <= dDown) {
				currentX = 	Verifier.nondetInt();
				currentY = Verifier.nondetInt();
			}
			else if(Verifier.nondetBoolean() && dUp < dDown) {
				currentX = 	Verifier.nondetInt();
				currentY = Verifier.nondetInt();
				currentZ += 1;
			}
			else if(cZdown != null) {
				currentX = 	Verifier.nondetInt();
				currentY = Verifier.nondetInt();
				currentZ -= 1;
			}
			else {
				return new Object();
			}
			
			nTurn++;
			
			if(nTurn >= Verifier.nondetInt())
				return new Object();
			
			if(currentX == x && currentY == y && currentZ == z) {
				return new Object();
			}
			
			
		}
	}
	

}
