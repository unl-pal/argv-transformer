package nl.blissfulthinking.java.android.apeforandroid;

import android.util.Log;




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
	
	 public static int MAX_VALUE = FP.fromInt(32767);
	 public static int HALF_VALUE = FP.fromInt(16383);
	 public static int ONE = FP.fromInt(1);
	 public static int TWO = FP.fromInt(2);
	 public static int SMALL = FP.fromFloat(0.00001f);

     /** Round to nearest fixed point integer */ 
     public static final int round(int n) { 
          if (n > 0) { 
               if ((n & 0x8000) != 0) { 
                    return (((n + 0x10000) >> 16) << 16); 
               } else { 
                    return (((n) >> 16) << 16); 
               } 
          } else { 
               int k; 
               n = -n; 
               if ((n & 0x8000) != 0) { 
                    k = (((n + 0x10000) >> 16) << 16); 
               } else { 
                    k = (((n) >> 16) << 16); 
               } 
               return -k; 
          } 
     }
     
     public static final int PI = 205887; 
     public static final int PI_OVER_2 = PI/2; 
     public static final int PI_OVER_4 = PI/4;
     public static final int E = 178145; 
     public static final int HALF = 2 << 15; 
      
      
     static final int SK1 = 498; 
     static final int SK2 = 10882; 

     static final int CK1 = 2328; 
     static final int CK2 = 32551; 

     /** 
      * Computes tan(f), f is a fixed point number in radians. 0 <= f <= PI/4 
      */ 

     static final int TK1 = 13323; 
     static final int TK2 = 20810; 
      
     static final int AS1 = -1228; 
     static final int AS2 = 4866; 
     static final int AS3 = 13901; 
     static final int AS4 = 102939; 

     /** 
      * This table is created using base of e. 
      * 
      * (defun fixedpoint (z) (round (* z (lsh 1 16)))) 
      * 
      * (loop for k from 0 to 16 do (setq z (log (+ 1 (expt 2.0 (- (+ k 1)))))) 
      * (insert (format "%d\n" (fixedpoint z)))) 
      */ 
     static final int[] log2arr = { 26573, 14624, 7719, 3973, 2017, 1016, 510, 256, 
               128, 64, 32, 16, 8, 4, 2, 1, 0, 0, 0 }; 

     static final int[] lnscale = { 0, 45426, 90852, 136278, 181704, 227130, 272557, 
               317983, 363409, 408835, 454261, 499687, 545113, 590539, 635965, 
               681391, 726817 }; 

     public static int ln(int x) { 
          // prescale so x is between 1 and 2 
          int shift = 0; 

          while (x > 1 << 17) { 
               shift++; 
               x >>= 1; 
          } 

          int g = 0; 
          int d = HALF; 
          for (int i = 1; i < 16; i++) { 
               if (x > ((1 << 16) + d)) { 
                    x = div(x, ((1 << 16) + d)); 
                    g += log2arr[i - 1]; // log2arr[i-1] = log2(1+d); 
               } 
               d >>= 1; 
          } 
          return g + lnscale[shift]; 
     }
     
     // The x,y point where two lines intersect
     public static int xIntersect;
     public static int yIntersect;

     /**
      * Does line segment A intersection line segment B?
      *
      * Assumes 16 bit fixed point numbers with 16 bits of fraction.
      *
      * For debugging, side effect xint, yint, the intersection point.
      *
      */
     public static boolean intersects (int ax0, int ay0, int ax1, int ay1,
 			int bx0, int by0, int bx1, int by1) {
 	
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
 	    int dist = Math.abs(div((ax0+ax1)-(bx0+bx1), TWO));
 	    return (dist == 0);
 	} else if (adx == 0) { // A  vertical
 	    int xa = div((ax0 + ax1), TWO);
 	    xmb = div(bdy,bdx);           // slope segment B
 	    xbb = by0 - mul(bx0, xmb); // y intercept of segment B
 	    xIntersect = xa;
 	    yIntersect = (mul(xmb,xIntersect)) + xbb;
 	} else if ( bdx == 0) { // B vertical
 	    int xb = div((bx0+bx1), TWO);
 	    xma = div(ady,adx);           // slope segment A
 	    xba = ay0 - (mul(ax0,xma)); // y intercept of segment A
 	    xIntersect = xb;
 	    yIntersect = (mul(xma,xIntersect)) + xba;
 	} else {
 	     xma = div(ady,adx);           // slope segment A
 	     xba = ay0 - (mul(ax0, xma)); // y intercept of segment A

 	     xmb = div(bdy,bdx);           // slope segment B
 	     xbb = by0 - (mul(bx0,xmb)); // y intercept of segment B
 	
 	     // parallel lines? 
 	     if (xma == xmb) {
 		 // Need trig functions
 		 int dist = Math.abs(mul((xba-xbb),
 					   (cos(atan(div((xma+xmb), TWO))))));
 		 if (dist < (1<<16) ) {
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
 		 xIntersect = div((xbb-xba),(xma-xmb));
 		 yIntersect = (mul(xma,xIntersect)) + xba;
 	     }
 	}

 	// After the point or points of intersection are calculated, each
 	// solution must be checked to ensure that the point of intersection lies
 	// on line segment A and B.
 	
 	int minxa = Math.min(ax0, ax1);
 	int maxxa = Math.max(ax0, ax1);

 	int minya = Math.min(ay0, ay1);
 	int maxya = Math.max(ay0, ay1);

 	int minxb = Math.min(bx0, bx1);
 	int maxxb = Math.max(bx0, bx1);

 	int minyb = Math.min(by0, by1);
 	int maxyb = Math.max(by0, by1);

 	return ((xIntersect >= minxa) && (xIntersect <= maxxa) && (yIntersect >= minya) && (yIntersect <= maxya) 
 		&& 
 		(xIntersect >= minxb) && (xIntersect <= maxxb) && (yIntersect >= minyb) && (yIntersect <= maxyb));
     }
     
} 