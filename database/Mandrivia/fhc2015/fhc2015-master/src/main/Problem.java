package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Problem {
	
	protected ProblemData data;
	
	/* solution build by problem [T] : [B] */
	protected int [][] move;
	
	public List<Integer> findPathTo(int x, int y, int z) {
		
		List<Integer> path = new ArrayList<Integer>();
		
		path.add(new Integer(1));

		Coord2 beg = this.computeCoord(this.data.getStartBalloon().x, this.data.getStartBalloon().y, 0);
		
		int currentX =  beg.x, currentY =  beg.y, currentZ = 0;
		
		int nTurn = 0;
		
		Coord2 target = new Coord2(x, y);
		
		while(true) {
			
			float Zratio = 0.75f;
			
			//Compute with current 
			int dCurr = 0;
			Coord2 cZcurr = computeCoord(currentX, currentY, currentZ);
			if(cZcurr != null) {
				dCurr = getDistance(cZcurr, target);
				if(z == currentZ)
					dCurr *= Zratio;
			}
			else
				dCurr = Integer.MAX_VALUE;
			
			int dUp = 0;
			Coord2 cZup = null;
			if(currentZ+1 >= data.getnZ())
				dUp = Integer.MAX_VALUE;
			else {
				cZup = computeCoord(currentX, currentY, currentZ+1);
				if(cZup != null) {
					dUp = getDistance(cZup, target);
					if(z > currentZ)
						dUp *= Zratio;	
				}
				else
					dUp = Integer.MAX_VALUE;
			}
			
			int dDown = 0;
			Coord2 cZdown = null;
			
			if(currentZ-1 < 0)
				dDown = Integer.MAX_VALUE;
			else {
				cZdown = computeCoord(currentX, currentY, currentZ-1);
				if(cZdown != null) {
					dDown = getDistance(cZdown, target);
					if(z < currentZ)
						dDown *= Zratio;		
				}
				else
					dDown = Integer.MAX_VALUE;
			}
			
			//best z
			if(cZcurr != null && dCurr <= dUp && dCurr <= dDown) {
				currentX = 	cZcurr.x;
				currentY = cZcurr.y;
				path.add(new Integer(0));
			}
			else if(cZup != null && dUp < dDown) {
				currentX = 	cZup.x;
				currentY = cZup.y;
				currentZ += 1;
				path.add(new Integer(1));
			}
			else if(cZdown != null) {
				currentX = 	cZdown.x;
				currentY = cZdown.y;
				currentZ -= 1;
				path.add(new Integer(-1));
			}
			else {
				return null;
			}
			
			nTurn++;
			
			if(nTurn >= this.data.getNbTurn())
				return null;
			
			if(currentX == x && currentY == y && currentZ == z) {
				return path;
			}
			
			
		}
	}
	

}
