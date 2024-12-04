package uk.ac.man.cs.patterns.battleship.domain.battle;

import uk.ac.man.cs.patterns.battleship.utils.Constants;
import uk.ac.man.cs.patterns.battleship.utils.RandomUtil;
import uk.ac.man.cs.patterns.battleship.domain.ships.Ship;
import java.util.ArrayList;
import java.util.List;
import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Observer;
import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Subject;
import uk.ac.man.cs.patterns.battleship.domain.ships.ShipFactory;

/**
 * Class that represent a board. A board is one of the main concepts in the game. Is a collection of position with ships in them.
 * @author Guillermo Antonio Toro Bayona
 */
public class Board extends Subject implements Observer {

    /**
     * List of Ships
     */
    private List<Ship> ships;
    /**
     * Integer that represents the number of ships available in the board.
     */
    private Integer shipsAvailable;
    /**
     * List of positions in the board, attacked 
     */
    private List<Position> positions;
    private List<Position> positionsVisited;

    /**
     * Method that localise each ship in the board with specific positions
     */
    private void localizeShipsInBoard() {
        List<Position> positionsOccupied = new ArrayList<Position>();
        // Loop for each ships
        for (Ship ship : this.ships) {
            // Validation of ship alocated
            boolean shipAllocated = false;
            // List of possible positios for the ships
            List<Position> possiblePositions = new ArrayList<Position>();            
            while (!shipAllocated) {
                // Take a random number to determine the direction.
                Integer randomPosition = RandomUtil.generateRandom(2);
                // Take a random X coordinate Y coordinate
                Integer x = RandomUtil.generateRandom(Constants.BOARD_SIZE_WIDTH);
                Integer y = RandomUtil.generateRandom(Constants.BOARD_SIZE_HEIGHT);
                // Loop against the ship size
                for (int i = 0; i < ship.getSize(); i++) {
                    // Create a initial position
                    Position position = null;
                    // Validate the random direction and Increase the coordinate
                    if (randomPosition == Constants.BOARD_DIRECTION_HORIZONTAL) {
                        position = new Position(x + i, y);
                    } else if (randomPosition == Constants.BOARD_DIRECTION_VERTICAL) {
                        position = new Position(x, y + i);
                    }
                    // Validate if the positions is valid
                    if (this.positions.contains(position) && !positionsOccupied.contains(position)) {
                        // Add to the possible positions
                        possiblePositions.add(position);
                        shipAllocated = true;
                    } else {
                        // Initialise the list as new list
                        possiblePositions = new ArrayList<Position>();
                        shipAllocated = false;
                        break;
                    }
                }
            }
            // Set the positions occupied by the ship.
            positionsOccupied.addAll(possiblePositions);
            ship.setPositionsOccupied(possiblePositions);
        }
    }
}
