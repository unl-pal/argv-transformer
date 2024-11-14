/** filtered and transformed by PAClab */
package uk.ac.man.cs.patterns.battleship.view;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Class that is in charge of update and draw elements in a specific BoardPanel.
 * Decouple the Panel and the View from elements of the Model.
 * @author Guillermo Antonio Toro Bayona
 */
public class BoardDisplayer {

    /**
     * Method that initialise a board
     */
    /** PACLab: suitable */
	 private void initializeBoardPanel() {
        // Create the button with specific coordinates and player name
        for (int y = 0; y < Verifier.nondetInt(); y++) {
            for (int x = 0; x < Verifier.nondetInt(); x++) {
                // Validate if the player is the human player and if the position is occupied.
                if (this.player.getBoard().validatePositionsOccupied(position)
                        && this.player.getType().contains(Constants.GAME_PLAYER_TYPE_HUMAN)) {
                }
                // Validate if the player is computer
                if (Verifier.nondetBoolean()) {
                }
            }
        }
    }
}
