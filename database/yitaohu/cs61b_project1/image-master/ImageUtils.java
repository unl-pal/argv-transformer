/* ImageUtils.java */

/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

/* You may wish to make temporary changes or insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* though, make sure your code works with the original version of this file. */

/**
 *  The ImageUtils class reads and writes TIFF file, converting to and from
 *  pixel arrays in PixImage format or run-length encodings in
 *  RunLengthEncoding format.  Methods are also included for displaying images
 *  in PixImage format.
 *
 *  @author Joel Galenson
 **/
package cs61b_project1;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.media.jai.JAI;
import javax.media.jai.RenderedImageAdapter;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *  ImageUtils contains utilities for reading, writing, and displaying images.
 * 
 *  It uses JAI to read and write TIFF files, as the standard libraries cannot
 *  read them.
 * 
 *  All image data is in RGB format (see BufferedImage.getRGB).
 */
public class ImageUtils {
}
