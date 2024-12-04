package uk.ac.man.cs.patterns.battleship.view;

import uk.ac.man.cs.patterns.battleship.view.listeners.GameListener;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import uk.ac.man.cs.patterns.battleship.domain.battle.Board;
import uk.ac.man.cs.patterns.battleship.domain.battle.Game;
import uk.ac.man.cs.patterns.battleship.domain.battle.Player;
import uk.ac.man.cs.patterns.battleship.domain.battle.Position;
import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Observer;
import uk.ac.man.cs.patterns.battleship.domain.battle.observer.Subject;
import uk.ac.man.cs.patterns.battleship.domain.ships.Ship;
import uk.ac.man.cs.patterns.battleship.utils.Constants;
import uk.ac.man.cs.patterns.battleship.utils.PropertiesUtil;

/**
 * Class that is in charge of update and draw elements in a specific BoardPanel.
 * Decouple the Panel and the View from elements of the Model.
 * @author Guillermo Antonio Toro Bayona
 */
public class BoardDisplayer implements Observer {

    /**
     * BoardPanel to update and draw.
     */
    private BoardPanel boardPanel;
    /**
     * Reference to a player owner of the board
     */
    private Player player;
    /**
     * BattleShipMainFrame frame of the game
     */
    private BattleShipMainFrame battleShipMainFrame;
    /**
     * GameListener to catch events
     */
    private GameListener gameListener;

    /**
     * Method that initialise a board
     */
    private void initializeBoardPanel() {
        // Set the values based on the information of the game, player
        this.boardPanel.getjLabelPlayerName().setText(this.player.getName());
        this.boardPanel.getjLabelShipsAvailable().setText(this.player.getBoard().getShipsAvailable().toString());
        this.boardPanel.setNotificationMessage(Constants.GAME_TEXT_SEPARATOR);
        this.boardPanel.getjPanelGridPositions().setLayout(new GridLayout(Constants.BOARD_SIZE_HEIGHT, Constants.BOARD_SIZE_WIDTH));
        // Create the button with specific coordinates and player name
        for (int y = 0; y < Constants.BOARD_SIZE_HEIGHT; y++) {
            for (int x = 0; x < Constants.BOARD_SIZE_WIDTH; x++) {
                // Create a position with coordinates
                Position position = new Position(x, y);
                // Create a button
                JButton jButtonInGrid = new JButton();
                // Create a defaul image icon
                ImageIcon imageIcon = new ImageIcon(Constants.GAME_PATH_IMAGE_SEA);
                // Validate if the player is the human player and if the position is occupied.
                if (this.player.getBoard().validatePositionsOccupied(position)
                        && this.player.getType().contains(Constants.GAME_PLAYER_TYPE_HUMAN)) {
                    // Set new image icon with the ship position
                    imageIcon = new ImageIcon(Constants.GAME_PATH_IMAGE_SHIP);
                }
                // Set the icon to the button
                jButtonInGrid.setIcon(imageIcon);
                // Set the name of the button for the player
                jButtonInGrid.setName(this.player.getName() + Constants.GAME_TEXT_SEPARATOR + x + Constants.GAME_TEXT_SEPARATOR + y);
                // Validate if the player is computer
                if (this.player.getType().contains(Constants.GAME_PLAYER_TYPE_COMPUTER)) {
                    // Set the action listener to the buttons of the computer player
                    jButtonInGrid.addActionListener(this.gameListener);
                }
                this.boardPanel.getjPanelGridPositions().add(jButtonInGrid);
            }
        }
        // Take the ship for the player
        for (Ship ship : this.player.getBoard().getShips()) {
            this.update(ship);
        }
    }
}
