package uk.ac.man.cs.patterns.battleship.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import uk.ac.man.cs.patterns.battleship.utils.Constants;
import uk.ac.man.cs.patterns.battleship.utils.PropertiesUtil;

/**
 * Class that represent a Board in the GUI.
 * @author Guillermo Antonio Toro Bayona
 */
public class BoardPanel extends JPanel {

    /**
     * JLabel for display info in the panel
     */
    private JLabel jLabelAirCraft;
    private JLabel jLabelBoat;
    private JLabel jLabelDestroyer;
    private JLabel jLabelCruiser;
    private JLabel jLabelSubmarine;
    private JLabel jLabelNotificationMessage;
    private JLabel jLabelPlayerName;
    private JLabel jLabelPlayerNameLabel;
    private JLabel jLabelShipsAvailable;
    private JLabel jLabelShipsAvailableLabel;
    /**
     * JPanel grid positions
     */
    private JPanel jPanelGridPositions;
    /**
     * JPanel info
     */
    private JPanel jPanelInfo;
    /**
     * JPanel message
     */
    private JPanel jPanelMessage;
    /**
     * JPanel ships info
     */
    private JPanel jPanelShipsInfo;
}
