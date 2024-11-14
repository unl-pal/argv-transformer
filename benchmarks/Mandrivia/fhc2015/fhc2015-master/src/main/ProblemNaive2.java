/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ProblemNaive2 {

	public void resolve() {
		try {
		} catch (FileNotFoundException e) {
		}
		
		
		
		for(int k=0; k<Verifier.nondetInt(); k++) {
		
			int n = 0;
			
			for(int i=0; i<Verifier.nondetInt(); i++) {
				for(int j=0; j<Verifier.nondetInt(); j++) {
					if({
					} != null) {
						int score = Verifier.nondetInt();
						int score2 = Verifier.nondetInt();
						n++;
					}
				}
			}
		}
	}

	
	/**
	 * Return coverage score for the case (x, y)
	 */
	/** PACLab: suitable */
	 public int getScoreBalloon(int x, int y) {
		int score = 0;
		
		// If the balloon is not in the map
		if(y >= Verifier.nondetInt() || y < 0)
			return -1;
		
		// (x - u)^2 + (columndist(y, v))^2 < V^2 => score + 1
		for(int i = (x - Verifier.nondetInt()) % this.data.getnX(); 
				i <= Verifier.nondetInt(); i++) {
			for(int j = y - Verifier.nondetInt();
					j <= y + Verifier.nondetInt(); j++) {
				if(this.data.isTarget(i,j) && Verifier.nondetInt() == 0)
					score++;
			}
		}
		return score;
	}

}
