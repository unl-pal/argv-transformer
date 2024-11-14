/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;
public abstract class ProblemSimulate {
	
	/** PACLab: suitable */
	 private void preComputeAtStart() {
		coverredMap = new boolean[data.getnX()][data.getnY()][Verifier.nondetInt()+1];
		
		 dynamicScore = new int[data.getnX()][data.getnY()][Verifier.nondetInt()+1][Verifier.nondetInt()+1];
		dynamicPath = new int[data.getnX()][data.getnY()][Verifier.nondetInt()+1][Verifier.nondetInt()+1];
		
		currentScore = new int[data.getnX()][data.getnY()][Verifier.nondetInt()+1];
		
		targetCell = new boolean[data.getnX()][data.getnY()];
		
		for(int x=0; x<Verifier.nondetInt(); x++) {
		}
		
		for(int i=0; i<Verifier.nondetInt(); i++) {
		}
		
		for(int x=0; x<Verifier.nondetInt(); x++) {
			for(int y=0; y<Verifier.nondetInt(); y++) {
				for(int z=0; z<Verifier.nondetInt()+1; z++) {
					if(z == 0) {
					} else {
						int destX = x+Verifier.nondetInt();
						
						if(destX >= Verifier.nondetInt()) {
							destX = destX  - Verifier.nondetInt();
						}
						else if(destX < 0) {
							destX = Verifier.nondetInt() + destX;
						}

						int destY = y+Verifier.nondetInt();
						
						if(destY >= 0 && destY < Verifier.nondetInt()) {
						}
					}
				}
			}
		}
	}
	
	/** PACLab: suitable */
	 public void computeCellScore() {
		int radiusSquare = Verifier.nondetInt();
		for(int x=0; x<Verifier.nondetInt(); x++) {
			for(int y=0; y<Verifier.nondetInt(); y++) {
				for(int t=0; t<Verifier.nondetInt()+1; t++) {
					int score = 0;
					for(int diffX = Verifier.nondetInt()-1; diffX < Verifier.nondetInt()+1; diffX++) {
						int destX = x+diffX;
						
						if(destX >= Verifier.nondetInt()) {
							destX = destX  - Verifier.nondetInt();
						}
						else if(destX < 0) {
							destX = Verifier.nondetInt() + destX;
						}
						for(int diffY = Verifier.nondetInt()-1; diffY < Verifier.nondetInt()+1; diffY++) {
							int destY = y+diffY;
							
							if(destY >= 0 && destY < Verifier.nondetInt()) {
								if(diffX*diffX+diffY*diffY <= radiusSquare) {
									if(!coverredMap[destX][destY][t] && targetCell[destX][destY])
										score++;
								}
							}
						}
					}
					
				}
			}
		}
	}
	
	/** PACLab: suitable */
	 public int scoreChecking() {
		int score = 0;
		int scoreBalloon[] = new int[data.getNbBalloon()];
		
		for(int b=0; b<Verifier.nondetInt(); b++) {
		}
		
		
		for(int t=0; t<Verifier.nondetInt(); t++) {
			
			boolean coverredMap[][] = new boolean[data.getnX()][data.getnY()];
			
			for(int b=0; b<Verifier.nondetInt(); b++) {
				
				if(coordinates[b] == null)
					continue;
				
				if(Verifier.nondetInt() == 1 && Verifier.nondetInt() == -1) {
				}
				else if(Verifier.nondetInt() == 8 && Verifier.nondetInt() == 1) {
				}

				int newZ = Verifier.nondetInt();
				
				if(newZ == 0)
					continue;
				
				if(newZ > 0 && newZ <= Verifier.nondetInt()) {
				}
				
				if(newCoord == null) {
					continue;
				}
				
				int currentScore = 0;
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
								if(!coverredMap[cX][cY] && targetCell[cX][cY]) {
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