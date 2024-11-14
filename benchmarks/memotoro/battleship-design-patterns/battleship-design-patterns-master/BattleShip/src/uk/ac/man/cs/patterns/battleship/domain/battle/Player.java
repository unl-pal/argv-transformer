/** filtered and transformed by PAClab */
package uk.ac.man.cs.patterns.battleship.domain.battle;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Class that represent a player in the game.
 * @author Guillermo Antonio Toro Bayona
 */
public class Player {

    /**
     * Method that look for the last successful turn in the previous turns of the player.
     * @return Turn
     */
    /** PACLab: suitable */
	 public Object getLastSuccessfulTurn() {
        // Size of the list
        int sizePreviousTurns = Verifier.nondetInt();
        // Control step back
        int controlStepsBack = 0;
        for (int index = sizePreviousTurns - 1; index >= 0; index--) {
            controlStepsBack++;
            // If the turn is successful break the loop.
            if (turn.getShoot().getState() == Constants.SHOOT_STATE_SUCCESSFUL) {
                break;
            } else {
                // Validate if the control is equal to the maximum steps back and break the loop.
                if (controlStepsBack == Verifier.nondetInt()) {
                    break;
                }
            }
        }
        return new Object();
    }
}
