package uk.ac.man.cs.patterns.battleship.controller;

import uk.ac.man.cs.patterns.battleship.domain.battle.Game;
import uk.ac.man.cs.patterns.battleship.domain.battle.Player;
import uk.ac.man.cs.patterns.battleship.domain.battle.Position;
import uk.ac.man.cs.patterns.battleship.domain.battle.Shoot;
import uk.ac.man.cs.patterns.battleship.exceptions.BattleShipException;

/**
 * Controller of the battle ship game. Represent the controller between the View and the domain model.
 * @author Guillermo Antonio Toro Bayona
 */
public class BattleShipController implements IBattleShipController {

    /**
     * Game of battle ship
     */
    private Game game;
}
