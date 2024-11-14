/** filtered and transformed by PAClab */
package main;

import org.sosy_lab.sv_benchmarks.Verifier;

public class ProblemNaive3 {
	
	/** PACLab: suitable */
	 public void resolve() {
		try {
		} catch (FileNotFoundException e) {
		}
		
		;
		final int scoreList[][][] = new int[this.data.getnX()][this.data.getnY()][this.data.getnZ()];
		try {
		} catch (FileNotFoundException e) {
		}
		
		try {
			while(Verifier.nondetBoolean()) {
				int x = Verifier.nondetInt();
				int y = Verifier.nondetInt();
				int z = Verifier.nondetInt();
				int score = Verifier.nondetInt();
			}
			
			
		} catch (IOException e) {
		}
		
		int targetScore[] = new int[data.getTargetsCase().size()];

		
		for(int i=0; i<Verifier.nondetInt(); i++) {
			for(int j=0; j<Verifier.nondetInt(); j++) {
				for(int k=0; k<Verifier.nondetInt(); k++) {
					if (Verifier.nondetInt() != 0) {
						for (int l = 0 ; l < Verifier.nondetInt() ; l++) {
							if (Verifier.nondetBoolean()) {
								targetScore[l]++;
								
							}
						}
					}
					if(Verifier.nondetInt() != 0) {
					}
				}
			}	
		}
		
	/*	for (int i = 0 ; i < targetScore.length ; i++) {
			System.out.println(targetScore[i]);
		}
		*/

		int [][][] scoreRareByPath = new int [data.getnX()][data.getnY()][data.getnZ()];
		
		for(int i=0; i<Verifier.nondetInt(); i++) {
			for(int j=0; j<Verifier.nondetInt(); j++) {
				for(int k=0; k<Verifier.nondetInt(); k++) {
					if (Verifier.nondetInt() != 0) {
					}
				}
			}
			
		}
		
	
		
		/*Collections.sort(sortedIndex, new Comparator<Coord3>() {

			public int compare(Coord3 index1, Coord3 index2) {
				return scoreList[index2.x][index2.y][index2.z]-scoreList[index1.x][index1.y][index1.z];
			}
		});
		
		List<List<Integer>> listPath = new ArrayList<List<Integer>>();
		
		int decalage = 0;
		
		for(int i=0; i<data.getNbBalloon(); i++) {
			
			if(i > 0) 
				while(commonPercentage(findPathTo(sortedIndex.get(i + decalage).x, sortedIndex.get(i + decalage).y, sortedIndex.get(i + decalage).z), 
						listPath.get(listPath.size()-1)) > 72)
					decalage = (decalage + 1) % sortedIndex.size();
					
			Coord3 c = sortedIndex.get(i + decalage);
			listPath.add(findPathTo(c.x, c.y, c.z));
			System.out.println(c.x+" "+c.y+" "+c.z);
		}
		
		int tempo = 1;
		
		for(int t=0; t<data.getNbTurn(); t++) {
			for(int i=0; i<data.getNbBalloon(); i++) {
				if(t >= listPath.get(i).size() || t-tempo*i < 0)
					this.move[t][i] = 0;
				else
					this.move[t][i] = listPath.get(i).get(t-tempo*i);
				
			}
		}*/


		
	}

}
