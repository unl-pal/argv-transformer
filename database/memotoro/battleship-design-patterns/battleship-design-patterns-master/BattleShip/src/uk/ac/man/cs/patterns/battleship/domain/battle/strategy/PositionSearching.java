package uk.ac.man.cs.patterns.battleship.domain.battle.strategy;

import uk.ac.man.cs.patterns.battleship.domain.battle.Player;
import uk.ac.man.cs.patterns.battleship.domain.battle.Position;

/**
 * Strategy Pattern. Abstract class to interchange different strategies to look for positions to attack.
 * @author Guillermo Antonio Toro Bayona
 */
public abstract class PositionSearching {

    /**
     * Position useful to calculate the next position to attack.
     */
    private Position positionHelper;
    /**
     * Player to be attacked. Is necessary to have access to its board.
     */
    private Player playerAttacked;
}
