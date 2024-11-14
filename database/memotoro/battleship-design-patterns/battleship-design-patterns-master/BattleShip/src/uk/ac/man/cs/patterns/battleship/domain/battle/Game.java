package uk.ac.man.cs.patterns.battleship.domain.battle;

import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Subject;
import uk.ac.man.cs.patterns.battleship.domain.battle.template.Turn;
import java.util.ArrayList;
import uk.ac.man.cs.patterns.battleship.utils.Constants;
import java.util.List;
import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Observer;
import uk.ac.man.cs.patterns.battleship.domain.battle.template.TurnFactory;
import uk.ac.man.cs.patterns.battleship.exceptions.BattleShipException;

/**
 * This class represent a session game.This class control specific steps before one turn was executed.
 * @author Guillermo Antonio Toro Bayona
 */
public class Game implements Observer {

    /**
     * Integer with the state of the game
     */
    private Integer state;
    /**
     * List of the players
     */
    private List<Player> players;
    /**
     * Player that is attacking.
     */
    private Player playerAttacking;
    /**
     * Player that is being attacked.
     */
    private Player playerAttacked;
}
