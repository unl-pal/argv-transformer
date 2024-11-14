/** filtered and transformed by PAClab */
package dungeonDigger.entities;

import org.sosy_lab.sv_benchmarks.Verifier;
/**
 * This class should handle all abilities, their animations, movement, collisions, and life-cycle.
 * Abilities should include all spells, skills, etc as well.
 * @author erbutler
 */
public class Ability {
	/** PACLab: suitable */
	 public void calculateMovement() {
		int speed = Verifier.nondetInt();
		boolean collided = Verifier.nondetBoolean();
		double distance = Verifier.nondetDouble();
		double intervals = Verifier.nondetDouble();
		int step = Verifier.nondetInt();
		if( step > intervals ) {
			distance = -1;
			step = 0;
			collided = true;
			return;
		}
		// This means we are just starting
		if( distance == -1 ) { 
			distance = Verifier.nondetDouble(); 
			intervals = distance / speed;
			step = 0;
		}
		int newX = Verifier.nondetInt(); 
		int newY = Verifier.nondetInt(); 

		//}
		// Repopulate Quads
		if( !this.getParentNode().contains(this) || Verifier.nondetInt() > 0 ) {
		}
		if( obstacles != null ) {
		}
		
		step++;
	}

}
