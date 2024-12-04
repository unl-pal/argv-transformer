package uk.ac.man.cs.patterns.battleship.domain.battle.template;

import uk.ac.man.cs.patterns.battleship.domain.battle.Player;
import uk.ac.man.cs.patterns.battleship.domain.battle.Position;
import uk.ac.man.cs.patterns.battleship.domain.battle.Shoot;
import uk.ac.man.cs.patterns.battleship.exceptions.BattleShipException;
import uk.ac.man.cs.patterns.battleship.utils.Constants;

/**
 * Template Pattern. Abstract class that represent a generic Turn. This class set specific steps in one player's turn.
 * Some specific steps must be implement by specific players. 
 * @author Guillermo Antonio Toro Bayona
 */
public abstract class Turn {

    /**
     * Shoot. Every turn implies a shoot.
     */
    private Shoot shoot;
    /**
     * Reference to the players who is attacking.
     */
    private Player playerAttacking;
    /**
     * Reference to the players who is being attacked.
     */
    private Player playerAttacked;
}
