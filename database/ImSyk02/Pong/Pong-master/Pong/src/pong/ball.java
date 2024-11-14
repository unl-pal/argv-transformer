
package pong;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * @author Beau Marwaha
 */
public class ball {
    
    
    private double dx;
    private double dy;
    private int x;
    private int y;
    private int speed;
    private final ImageIcon II = 
            new ImageIcon(this.getClass().getResource("images/ball.png"));
    private final Image IMAGE = II.getImage();
}
