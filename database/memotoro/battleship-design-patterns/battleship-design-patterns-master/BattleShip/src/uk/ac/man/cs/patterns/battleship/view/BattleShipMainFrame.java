package uk.ac.man.cs.patterns.battleship.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.ac.man.cs.patterns.battleship.utils.Constants;
import uk.ac.man.cs.patterns.battleship.utils.PropertiesUtil;
import uk.ac.man.cs.patterns.battleship.view.listeners.GameListener;

/**
 * Class that represent the main frame of the game
 * @author Guillermo Antonio Toro Bayona
 */
public class BattleShipMainFrame extends JFrame {

    /**
     * GameListener of the game
     */
    private GameListener gameListener;
    /**
     * JPanel for new button
     */
    private JPanel jPanelButton;
    /**
     * JPanel for game
     */
    private JPanel jPanelGame;
}
