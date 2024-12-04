
package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Beau Marwaha
 */
public class window extends JPanel implements ActionListener {
    
    final private Timer timer;
    private BufferedImage image;
    private BufferedImage leftScoreImage;
    private BufferedImage rightScoreImage;
    
    final private paddleLeft paddleLeft;
    final private paddleRight paddleRight;
    final private ball ball;
    
    private int leftPoints = 0;
    private int rightPoints = 0;
   
    private class TAdapter extends KeyAdapter {
    }
    
}
