package uk.ac.man.cs.patterns.battleship.view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import uk.ac.man.cs.patterns.battleship.controller.BattleShipController;
import uk.ac.man.cs.patterns.battleship.controller.IBattleShipController;
import uk.ac.man.cs.patterns.battleship.domain.battle.Game;
import uk.ac.man.cs.patterns.battleship.domain.battle.Player;
import uk.ac.man.cs.patterns.battleship.domain.battle.Shoot;
import uk.ac.man.cs.patterns.battleship.exceptions.BattleShipException;
import uk.ac.man.cs.patterns.battleship.utils.Constants;
import uk.ac.man.cs.patterns.battleship.utils.PropertiesUtil;
import uk.ac.man.cs.patterns.battleship.view.BattleShipMainFrame;
import uk.ac.man.cs.patterns.battleship.view.BoardDisplayer;

/**
 * Class that implement action listener to catch every action in the panels and frames
 * @author Guillermo Antonio Toro Bayona
 */
public class GameListener implements ActionListener {

    /**
     * Game of battle ship
     */
    private Game game;
    /**
     * Interface IBattleShipController controller
     */
    private IBattleShipController battleShipController;
    /**
     * BattleShipMainFrame with reference to the main frame.
     */
    private BattleShipMainFrame battleShipMainFrame;
    /**
     * BoardDisplayer for the human player
     */
    private BoardDisplayer boardPlayerDisplayer;
    /**
     * BoardDisplayer for computer player
     */
    private BoardDisplayer boardComputerDisplayer;
}
