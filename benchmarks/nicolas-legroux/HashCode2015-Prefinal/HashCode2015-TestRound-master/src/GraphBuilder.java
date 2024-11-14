import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphBuilder {
	
	Problem pb;
	Graph g;	
	Map< Pair, List<Part> > intersectingParts;
	Solution solution;
	
	int maxHam;
	
	public Graph build() {

		for(int s = 12; s >= 3; s--) {		
			for(int w = 1; w <= s; w++) {
				if(s%w == 0) // only if the part is possible					
					addPartsOfSize(w, s/w);
			}				
		}
		
		return g;
	}
	
	private boolean isPart(int x0, int y0, int w, int h) {
		int countHam = 0;
		
		for(int x = x0; x < x0 + w; x++) {
			for(int y = y0; y < y0 + h; y++) {
				if(!solution.isFree(x, y)){
					return false;
				}
				switch(pb.pizza[x][y]) {
				case HAM:
					countHam++;
					break;
				default:
					break;
				}
			}				
		}

		if(countHam >= 3 && countHam<=maxHam)
			return true;
		return false;
	}	
}