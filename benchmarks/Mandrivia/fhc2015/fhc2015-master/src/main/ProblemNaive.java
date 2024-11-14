/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ProblemNaive {
	
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
	
	/** PACLab: suitable */
	 public void resolve() {
		for(int t = 0; t < Verifier.nondetInt(); t++) {
			for(int b = 0; b < Verifier.nondetInt(); b++) {
				if(t == b) {
				} else if(t > b) {
					int bestAltitude = 0;
					int bestAltitudeValue = -1;
					for(int i = -1; i <= 1; i++) {
						if(Verifier.nondetInt() >= bestAltitudeValue) {
							bestAltitude = i;
							bestAltitudeValue = Verifier.nondetInt();
						}
					}
				}
			}
		}
	}

}
