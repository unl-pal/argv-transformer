/** filtered and transformed by PAClab */
package nl.blissfulthinking.java.android.apeforandroid;

import org.sosy_lab.sv_benchmarks.Verifier;




/** 
* 
* 16:16 fixed point math routines, for IAppli/CLDC platform. 
* A fixed point number is a 32 bit int containing 16 bits of integer and 16 bits of fraction. 
*<p> 
* (C) 2001 Beartronics 
* Author: Henry Minsky (hqm@alum.mit.edu) 
*<p> 
* Licensed under terms "Artistic License"<br> 
* <a href="http://www.opensource.org/licenses/artistic-license.html">http://www.opensource.org/licenses/artistic-license.html</a><br> 
* 
*<p> 
* Numerical algorithms based on 
* http://www.cs.clemson.edu/html_docs/SUNWspro/common-tools/numerical_comp_guide/ncg_examples.doc.html 
* <p> 
* Trig routines based on numerical algorithms described in 
* http://www.magic-software.com/MgcNumerics.html 
* 
* http://www.dattalo.com/technical/theory/logs.html 
* 
* @version $Id: FP.java,v 1.6 2001/04/05 07:40:17 hqm Exp $ 
*/ 

public class FP { 
	
	 /** Round to nearest fixed point integer */ 
     /** PACLab: suitable */
	 public static final int round(int n) { 
          if (n > 0) { 
               if (Verifier.nondetInt() != 0) { 
                    return Verifier.nondetInt(); 
               } else { 
                    return Verifier.nondetInt(); 
               } 
          } else { 
               int k; 
               n = -n; 
               if (Verifier.nondetInt() != 0) { 
                    k = (Verifier.nondetInt() << 16); 
               } else { 
                    k = (Verifier.nondetInt() << 16); 
               } 
               return -k; 
          } 
     }
     
     /** PACLab: suitable */
	 public static int ln(int x) { 
          int HALF = Verifier.nondetInt();
		// prescale so x is between 1 and 2 
          int shift = 0; 

          while (x > Verifier.nondetInt()) { 
               shift++; 
               x >>= 1; 
          } 

          int g = 0; 
          int d = HALF; 
          for (int i = 1; i < 16; i++) { 
               if (x > Verifier.nondetInt()) { 
                    x = Verifier.nondetInt(); 
                    g += log2arr[i - 1]; // log2arr[i-1] = log2(1+d); 
               } 
               d >>= 1; 
          } 
          return Verifier.nondetInt(); 
     }
     
     /**
      * Does line segment A intersection line segment B?
      *
      * Assumes 16 bit fixed point numbers with 16 bits of fraction.
      *
      * For debugging, side effect xint, yint, the intersection point.
      *
      */
     /** PACLab: suitable */
	 public static boolean intersects (int ax0, int ay0, int ax1, int ay1,
 			int bx0, int by0, int bx1, int by1) {
 	
 	int yIntersect = Verifier.nondetInt();
				int xIntersect = Verifier.nondetInt();
	ax0 <<= 16;
 	ay0 <<= 16;
 	ax1 <<= 16;
 	ay1 <<= 16;
 	
 	bx0 <<= 16;
 	by0 <<= 16;
 	bx1 <<= 16;
 	by1 <<= 16;
 	
 	int adx = (ax1 - ax0);
 	int ady = (ay1 - ay0);
 	int bdx = (bx1 - bx0);
 	int bdy = (by1 - by0);

 	int xma;
 	int xba;

 	int xmb;
 	int xbb;	
 	int TWO = (2 << 16);

 	if ((adx == 0) && (bdx == 0)) { // both vertical lines
 	    int dist = Verifier.nondetInt();
 	    return (dist == 0);
 	} else if (adx == 0) { // A  vertical
 	    int xa = Verifier.nondetInt();
 	    xmb = Verifier.nondetInt();           // slope segment B
 	    xbb = by0 - Verifier.nondetInt(); // y intercept of segment B
 	    xIntersect = xa;
 	    yIntersect = Verifier.nondetInt() + xbb;
 	} else if ( bdx == 0) { // B vertical
 	    int xb = Verifier.nondetInt();
 	    xma = Verifier.nondetInt();           // slope segment A
 	    xba = ay0 - Verifier.nondetInt(); // y intercept of segment A
 	    xIntersect = xb;
 	    yIntersect = Verifier.nondetInt() + xba;
 	} else {
 	     xma = Verifier.nondetInt();           // slope segment A
 	     xba = ay0 - Verifier.nondetInt(); // y intercept of segment A

 	     xmb = Verifier.nondetInt();           // slope segment B
 	     xbb = by0 - Verifier.nondetInt(); // y intercept of segment B
 	
 	     // parallel lines? 
 	     if (xma == xmb) {
 		 // Need trig functions
 		 int dist = Verifier.nondetInt();
 		 if (dist < Verifier.nondetInt() ) {
 		     return true;
 		 } else {
 		     return false;
 		 }
 	     } else {
 		 // Calculate points of intersection
 		 // At the intersection of line segment A and B, XA=XB=XINT and YA=YB=YINT
 		 if ((xma-xmb) == 0) {
 		     return false;
 		 }
 		 xIntersect = Verifier.nondetInt();
 		 yIntersect = Verifier.nondetInt() + xba;
 	     }
 	}

 	// After the point or points of intersection are calculated, each
 	// solution must be checked to ensure that the point of intersection lies
 	// on line segment A and B.
 	
 	int minxa = Verifier.nondetInt();
 	int maxxa = Verifier.nondetInt();

 	int minya = Verifier.nondetInt();
 	int maxya = Verifier.nondetInt();

 	int minxb = Verifier.nondetInt();
 	int maxxb = Verifier.nondetInt();

 	int minyb = Verifier.nondetInt();
 	int maxyb = Verifier.nondetInt();

 	return ((xIntersect >= minxa) && (xIntersect <= maxxa) && (yIntersect >= minya) && (yIntersect <= maxya) 
 		&& 
 		(xIntersect >= minxb) && (xIntersect <= maxxb) && (yIntersect >= minyb) && (yIntersect <= maxyb));
     }
     
} 