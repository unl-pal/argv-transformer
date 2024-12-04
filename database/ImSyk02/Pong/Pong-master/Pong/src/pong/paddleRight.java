
package pong;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * @author Beau Marwaha
 */
public class paddleRight {
    
    private int dy;
    private int x;
    private int y;
    private final ImageIcon II = new ImageIcon(this.getClass().getResource("images/paddle.png"));
    private final Image IMAGE = II.getImage();
    //use of booleans with movement prevents lag when instantly switching directions
    private boolean up = false;
    private boolean down = false;
}
