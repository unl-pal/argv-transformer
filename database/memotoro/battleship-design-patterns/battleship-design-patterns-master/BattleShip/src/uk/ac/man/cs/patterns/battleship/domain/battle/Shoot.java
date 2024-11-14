package uk.ac.man.cs.patterns.battleship.domain.battle;

import uk.ac.man.cs.patterns.battleship.domain.battle.strategy.PositionSearching;

/**
 * Shoot.Represent a shot in the game.
 * @author Guillermo Antonio Toro Bayona
 */
public class Shoot {

    /**
     * Integer with the state of the shoot.
     */
    private Integer state;
    /**
     * Position attacked in the shoot.
     */
    private Position position;
    /**
     * PositionSearching related with the strategy to be selected to create positions.
     */
    private PositionSearching positionSearching;
}
