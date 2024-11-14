/* PixImage.java */
/** filtered and transformed by PAClab */
package cs61b_project1;

import org.sosy_lab.sv_benchmarks.Verifier;
/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

public class PixImage {

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  /** PACLab: suitable */
 public void setPixel(int x, int y, short red, short green, short blue) {
    // Your solution here.
	  if((-1)<red && red<256&&(-1)<green&&green<256&&(-1)<blue&&blue<256){
		  
	  }
  }

  /**
   * toString() returns a String representation of this PixImage.
   *
   * This method isn't required, but it should be very useful to you when
   * you're debugging your code.  It's up to you how you represent a PixImage
   * as a String.
   *
   * @return a String representation of this PixImage.
   */
  /** PACLab: suitable */
 public Object toString() {
    int width = Verifier.nondetInt();
	int height = Verifier.nondetInt();
	for(int j=0;j<height;j++){
		  for(int i=0;i<width;i++){
			  if(i!=(width-1)){
			  }
			  
		  }
		  
	  }
	  return new Object();
  }

  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  /** PACLab: suitable */
 public Object sobelEdges() {
    int height = Verifier.nondetInt();
	int width = Verifier.nondetInt();
	// Replace the following line with your solution.
	  int[][][] gx=new int[width][height][3];
	  int[][][] gy=new int[width][height][3];
	  long[][] energy=new long[width][height];
	  
	  int[][] gxC={{1,2,1},{0,0,0},{-1,-1,-1}};
	  int[][] gyC={{1,0,-1},{2,0,-2},{1,0,-1}};
	  
	  for(int x=0;x<width;x++){
		  for(int y=0;y<height;y++){
			  for(int k=0;k<3;k++){
				  
				  int istart=0;
				  int jstart=0;
				  int iend=3;
				  int jend=3;
				  
				  if(0==x){
					  istart=1;
				  }
				  if(0==y){
					  jstart=1;
				  }
				  if((width-1)==x){
					  iend=2;
				  }
				  if((height-1)==y){
					  jend=2;
				  }
				  
				  
				  for(int i=istart;i<iend;i++){
					  for(int j=jstart;j<jend;j++){
					  }
				  }
				  
				  
			  }//loop k
			  
			  short color=Verifier.nondetInt();
		  }//loop y
		  
	  }//loop x
	  
	  
	  
	  return new Object();
    // Don't forget to use the method mag2gray() above to convert energies to
    // pixel intensities.
  }
}
