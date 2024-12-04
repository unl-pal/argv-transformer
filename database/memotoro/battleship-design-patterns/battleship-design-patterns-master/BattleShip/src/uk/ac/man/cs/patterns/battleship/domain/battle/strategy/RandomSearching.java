package uk.ac.man.cs.patterns.battleship.domain.battle.strategy;

import uk.ac.man.cs.patterns.battleship.domain.battle.Player;
import uk.ac.man.cs.patterns.battleship.domain.battle.Position;

/**
 * Strategy Pattern.Random Strategy is a concrete strategy to look for position randomly.
 * The position to attack are selected randomly from the available position in the board.
 * @author Guillermo Antonio Toro Bayona
 */
public class RandomSearching extends PositionSearching {
}
