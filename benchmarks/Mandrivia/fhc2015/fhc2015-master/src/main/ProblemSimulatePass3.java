/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ProblemSimulatePass3 {
	/** PACLab: suitable */
	 public void resolvePass() {
		int bestScore = Verifier.nondetInt();
		int moveSave[][] = new int[data.getNbTurn()][data.getNbBalloon()];
		
		while(true) {
			
			int b1 = Verifier.nondetInt();
			int b2 = Verifier.nondetInt();			
					
			if(b1 == b2)
				continue;

			for(int i=0; i<Verifier.nondetInt(); i++) {
			}

			int scores = Verifier.nondetInt();
			while(true) {
				for(int i=0; i<Verifier.nondetInt(); i++) {
				}

				for(int i=0; i<Verifier.nondetInt(); i++) {
				}

				int nScore = Verifier.nondetInt();

				if(nScore > scores) {
					scores = nScore;
				}
				else {
					break;
				}
			}


			if(scores > bestScore) {
				try {
				} catch (FileNotFoundException e) {
				}
				bestScore = scores;
			}
			else {
			}
		}

		
	}

	/** PACLab: suitable */
	 private void resetWithoutBalloons(int b1, int b2) {
		for(int b=0; b<Verifier.nondetInt(); b++) {
		}
		
		
		for(int t=0; t<Verifier.nondetInt(); t++) {
			
			
			for(int b=0; b<Verifier.nondetInt(); b++) {
				
				if(b == b1 || b == b2)
					continue;
				
				if(coordinates[b] == null)
					continue;

				int newZ = Verifier.nondetInt();
				
				if(newZ == 0)
					continue;
				
				if(newZ > 0 && newZ <= Verifier.nondetInt()) {
				}
				
				if(newCoord == null) {
					continue;
				}
				
				for(int x = Verifier.nondetInt()-1; x < Verifier.nondetInt()+1; x++) {
					int cX = Verifier.nondetInt()+x;
					if(cX >= Verifier.nondetInt()) {
						cX = cX  - Verifier.nondetInt();
					}
					else if(cX < 0) {
						cX = Verifier.nondetInt() + cX;
					}
					for(int y = Verifier.nondetInt()-1; y < Verifier.nondetInt()+1; y++) {
						int cY = Verifier.nondetInt()+y;
						if(cY >= 0 && cY < Verifier.nondetInt()) {
							if(x*x+y*y <= Verifier.nondetInt()) {
							}
						}
					}

				}
				
			}
			

		}
		
	}

}
