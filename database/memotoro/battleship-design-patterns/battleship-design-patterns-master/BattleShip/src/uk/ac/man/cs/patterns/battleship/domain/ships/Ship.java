package uk.ac.man.cs.patterns.battleship.domain.ships;

import uk.ac.man.cs.patterns.battleship.utils.Constants;
import uk.ac.man.cs.patterns.battleship.domain.battle.Position;
import java.util.ArrayList;
import java.util.List;
import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Subject;

/**
 * Ship. This class represent a ship as a general concept. Concrete ships are related with Ship with inheritance relationship.
 * @author Guillermo Antonio Toro Bayona
 */
public abstract class Ship extends Subject {

    /**
     * Integer that represent the size
     */
    private Integer size;
    /**
     * String name
     */
    private String name;
    /*
     * Integer that represent the state
     */
    private Integer state;
    /**
     * List of Position that the ship is occupying in the board and attacked positions.
     */
    private List<Position> positionsOccupied;
    private List<Position> positionsAttacked;
}
