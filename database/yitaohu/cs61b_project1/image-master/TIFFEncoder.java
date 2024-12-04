/* TIFFEncoder.java */

/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

/* You may wish to make temporary changes or insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* though, make sure your code works with the original version of this file. */

/**
 *  The TIFFEncoder class allows us to write a TIFF file from a pixel array
 *  in PixImage format or from a run-length encoding in RunLengthEncoding
 *  format.  A TIFF file written from a RunLengthEncoding is compressed as
 *  a run-length encoding, so it may be much shorter than a TIFF file written
 *  from a PixImage.
 *
 *  @author Joel Galenson
 **/
package cs61b_project1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.stream.FileImageOutputStream;

public class TIFFEncoder {

  /**
   * The TiffType enum represents the type of a value of a TIFF field,
   * which can have a variety of sizes.
   */
  private static enum TiffType { SHORT, LONG }
}
