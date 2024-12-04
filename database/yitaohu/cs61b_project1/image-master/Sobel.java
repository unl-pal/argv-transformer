/* Sobel.java */
package cs61b_project1;
/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

/* You may wish to make temporary changes or insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* though, make sure your code works with the original version of this file. */

/**
 *  The Sobel class is a program that reads an image file in TIFF format,
 *  performs Sobel edge detection and uses it to create a grayscale image
 *  showing the intensities of the strongest edges, writes the grayscale image
 *  as a TIFF file, and displays both images.  Optionally, it can also blur the
 *  image with one or more iterations of a 3x3 box blurring kernel (similar to
 *  our Blur program) before performing edge detection, which tends to make
 *  edge detection more robust.  If blurring is selected, this program writes
 *  both the blurred image and the grayscale-edge image to files and displays
 *  all three images (the input image and the two output images).
 *
 *  The Sobel program takes up to three parameters.  The first parameter is
 *  the name of the TIFF-format file to read.  (The output image file is
 *  constructed by adding "edge_" to the beginning of the input filename.)
 *  An optional second parameter specifies the number of iterations of the
 *  box blurring operation.  (The default is zero iterations.)  If a third
 *  parameter is present (regardless of what it is), a second output grayscale
 *  image is written, run-length encoded to reduce its file size, with prefix
 *  "rle_".  For example, if you run
 *
 *         java Sobel engine.tiff 5 blah
 *
 *  then Sobel will read engine.tiff, perform 5 iterations of blurring, perform
 *  Sobel edge detection, map the Sobel gradients to grayscale intensities,
 *  write the blurred image to blur_engine.tiff, write a grayscale-edge image
 *  to edge_engine.tiff, and write a run-length encoded grayscale-edge image to
 *  rle_engine.tiff.
 *
 *  @author Joel Galenson and Jonathan Shewchuk
 */

public class Sobel {
}
