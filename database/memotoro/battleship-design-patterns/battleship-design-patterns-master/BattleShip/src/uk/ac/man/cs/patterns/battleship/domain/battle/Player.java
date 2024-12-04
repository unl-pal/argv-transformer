package uk.ac.man.cs.patterns.battleship.domain.battle;

import uk.ac.man.cs.patterns.battleship.domain.battle.template.Turn;
import java.util.ArrayList;
import java.util.List;
import uk.ac.man.cs.patterns.battleship.utils.Constants;

/**
 * Class that represent a player in the game.
 * @author Guillermo Antonio Toro Bayona
 */
public class Player {

    /**
     * String with the name of the player.
     */
    private String name;
    /**
     * String with the type of the player.
     */
    private String type;
    /**
     * List of the previous turns of the player.
     */
    private List<Turn> previousTurns;
    /**
     * Board of the player.
     */
    private Board board;

    /**
     * Method that look for the last successful turn in the previous turns of the player.
     * @return Turn
     */
    public Turn getLastSuccessfulTurn() {
        // Size of the list
        int sizePreviousTurns = this.previousTurns.size();
        Turn turn = null;
        // Control step back
        int controlStepsBack = 0;
        for (int index = sizePreviousTurns - 1; index >= 0; index--) {
            controlStepsBack++;
            turn = this.previousTurns.get(index);
            // If the turn is successful break the loop.
            if (turn.getShoot().getState() == Constants.SHOOT_STATE_SUCCESSFUL) {
                break;
            } else {
                // Validate if the control is equal to the maximum steps back and break the loop.
                if (controlStepsBack == Constants.SHOOT_CONTROL_SUCCESSFUL) {
                    break;
                }
            }
        }
        return turn;
    }
}
