package main;

public class ProblemNaive extends Problem {
	
	/**
	 * Return coverage score for the case (x, y)
	 */
	public int getScoreBalloon(int x, int y) {
		int score = 0;
		
		// If the balloon is not in the map
		if(y >= this.data.getnY() || y < 0)
			return -1;
		
		// (x - u)^2 + (columndist(y, v))^2 < V^2 => score + 1
		for(int i = (x - this.data.getCoverageRadius()) % this.data.getnX(); 
				i <= (x + this.data.getCoverageRadius()) % this.data.getnX(); i++) {
			for(int j = y - (this.data.getCoverageRadius() - columnDist(x, i));
					j <= y + (this.data.getCoverageRadius() - columnDist(x, i)); j++) {
				if(this.data.isTarget(i,j) && this.data.isCovered(this.data.getTargetIndex(i, j)) == 0)
					score++;
			}
		}
		return score;
	}
	
	public void resolve() {
		for(int t = 0; t < this.data.getNbTurn(); t++) {
			System.out.println("Tour " + t);
			for(int b = 0; b < this.data.getNbBalloon(); b++) {
				if(t == b) {
					this.move[t][b] = 1;
					this.data.setBalloonsCoord(b, 
							this.data.newBalloonCoord(this.data.getBalloonsCoord(b), 1));
				} else if(t > b) {
					int bestAltitude = 0;
					int bestAltitudeValue = -1;
					for(int i = -1; i <= 1; i++) {
						if(nextTurnResult(this.data.getBalloonsCoord(b), i, 1) >= bestAltitudeValue) {
							bestAltitude = i;
							bestAltitudeValue = nextTurnResult(this.data.getBalloonsCoord(b), i, 1);
						}
					}
					this.move[t][b] = bestAltitude;
					this.updateTarget(this.data.newBalloonCoord(this.data.getBalloonsCoord(b), bestAltitude), 
							this.data.getBalloonsCoord(b));
					this.data.setBalloonsCoord(b, 
							this.data.newBalloonCoord(this.data.getBalloonsCoord(b), bestAltitude));
				}
			}
		}
	}

}
