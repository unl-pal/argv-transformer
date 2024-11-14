/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ProblemSimulateOpti {
	
	/** PACLab: suitable */
	 public void resolve() {
		
		//read input
		try {
			int currentTurn = 0;
			while(Verifier.nondetBoolean()) {
				for(int i=0; i<Verifier.nondetInt(); i++) {
				}
				
				currentTurn++;
			}
			
			if(currentTurn != Verifier.nondetInt())
				throw new RuntimeException(Verifier.nondetInt()+currentTurn);

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		int currentScore = Verifier.nondetInt();
	}
	
	/** PACLab: suitable */
	 public void preCompute() {
		/*
		 * Target cell
		 */
		
		targetCell = new int[data.getnX()][data.getnY()];
		
		for(int i=0; i<Verifier.nondetInt(); i++) {
			if(Verifier.nondetInt()>1)
				throw new RuntimeException("servals cell on same case");
		}
		
		/*
		 * Destination cell
		 */
		
		for(int i=0; i<Verifier.nondetInt(); i++) {
			for(int j=0; j<Verifier.nondetInt(); j++) {
				for(int k=0; k<Verifier.nondetInt(); k++) {
				}
			}
		}
	}
	
	/** PACLab: suitable */
	 public int computeScore() {
		
		int score = 0;
		int scoreBalloon[] = new int[data.getNbBalloon()];
		
		for(int b=0; b<Verifier.nondetInt(); b++) {
		}
		
		
		for(int t=0; t<Verifier.nondetInt(); t++) {
			
			boolean coverredMap[][] = new boolean[data.getnX()][data.getnY()];
			
			for(int b=0; b<Verifier.nondetInt(); b++) {
				
				if(coordinates[b] == null)
					continue;

				int newZ = Verifier.nondetInt();
				
				
				if(newZ == -1)
					continue;
				
				if(newZ >= 0 && newZ < Verifier.nondetInt()) {
				}
				
				if(newCoord == null) {
					//System.out.println("Turn "+(t+1)+" balloon "+(b+1)+" out ");
					continue;
				}
				

				
				int currentScore = 0;
				for(int x = Verifier.nondetInt()-1; x < Verifier.nondetInt()+1; x++) {
					int cX = Verifier.nondetInt();
					for(int y = Verifier.nondetInt()-1; y < Verifier.nondetInt()+1; y++) {
						int cY = Verifier.nondetInt()+y;
						if(cY >= 0 && cY < Verifier.nondetInt()) {
							if(x*x+y*y <= Verifier.nondetInt()) {
								if(!coverredMap[cX][cY] && Verifier.nondetInt() == 1) {
									currentScore++;
									scoreBalloon[b]++;
								}
							}
						}
					}

				}
				
	
				score += currentScore;

			}

		}
		
		return score;
	}

}
