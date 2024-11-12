#define   XMIN    1.0e-10
#define   XLARGE  4.9     /* changed by RLG from 6.375, since was getting res == 1.0  in x < XLARGE block */
#define   SQRPI    .5641895835477563
#define   SQR2    1.41421356237309
#define   OSR2PI  0.39894228040143267793

#include <stdio.h>
#include <math.h>

/*  Gauss usage:   dllcall invcdfn( xmat, nelem );   
    where nelem is number of elements in matrix xmat
*/

/* 
    PROBLEM:  pheev() for x < -8.3 returns  ZERO  !!

       x         matlab normcdf(x)    pheev(x)

    -8.60        3.98580496e-18     0.00000000e+00 
    -8.50        9.47953482e-18     0.00000000e+00 
    -8.40        2.23239320e-17     0.00000000e+00 
    -8.30        5.20556974e-17     5.55111512e-17 
    -8.20        1.20193515e-16     1.11022302e-16 
    -8.10        2.74795939e-16     2.77555756e-16 
    -8.00        6.22096057e-16     6.10622664e-16 
    -7.90        1.39451715e-15     1.38777878e-15 
    -7.80        3.09535877e-15     3.10862447e-15 
    -7.70        6.80331154e-15     6.82787160e-15 
    -7.60        1.48065375e-14     1.48214774e-14 
    -7.50        3.19089167e-14     3.19189120e-14 
    
*/


void pheev(double *ph)
     /* routine to compute value of std normal cdf */
     /* obtained from VAH and rewritten in C by Peter Boatwright */
     /* modified by Ron Goettler to :   */
     /*   1. use static declarations    */
     /*   2. replace  pow(x,2) with x*x */
     /*   3. #define various constants  */
     /*   4. use Chebyshev approximation for x > XLARGE */
{
  /*      double precision function pheev(y)                             */
  /* c===============================================================c   */
  /* c     written by Vassilis Hajivassiliou                         c   */
  /* c     with the aid of Yoosoon Chang.                            c   */
  /* c     version 1.0, March 22, 1992                               c   */
  /* c     version 1.1, July 11, 1992                                c   */
  /* c===============================================================c   */
  /* c     this essentially uses the imsl real*8 function erf, provided  */
  /* c     by clint cummins for use in quail5.1 . the modification using */
  /* c     the fact that phee(z)=0.5+0.5*erf(z/SQR2) was done by vah .   */
  /* c                                                                   */
  /* c+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  */
  double x, res, xsq, xnum, xden, xi;
  int i;
  static double p[5] = {
    113.8641541510502,
    377.4852376853020,
    3209.377589138469,
    0.1857777061846032,
    3.161123743870566 };
  static double q[4] = {
    244.0246379344442,
    1282.616526077372,
    2844.236833439171,
    23.60129095234412 };
  static double p1[9] = {
    8.883149794388376,
    66.11919063714163,
    298.6351381974001,
    881.9522212417691,
    1712.047612634071,
    2051.078377826071,
    1230.339354797997,
    2.153115354744038e-8,
    .5641884969886701 };
  static double q1[8] = {
    117.6939508913125,
    537.1811018620099,
    1621.389574566690,
    3290.799235733460,
    4362.619090143247,
    3439.367674143722,
    1230.339354803749,
    15.74492611070983 };
  static double p2[6] = {
    -3.603448999498044e-01,
    -1.257817261112292e-01,
    -1.608378514874228e-02,
    -6.587491615298378e-04,
    -1.631538713730210e-02,
    -3.053266349612323e-01 };
  static double q2[5] = {
    1.872952849923460,
    5.279051029514284e-01,
    6.051834131244132e-02,
    2.335204976268692e-03,
    2.568520192289822 };
  
  /* if *ph > 0 then would be taking erfc() of negative number, so will need to use  -res  if *ph > 0 */
  if ( *ph < 0.0)  x = -(*ph/SQR2);  /* matlab erfcore.m says more accurate for low x to use  .5*erfc(-x) instead of .5*(1+erf(x)) */
  else             x =   *ph/SQR2;   /* also changed VAH last line from     *ph = 0.5 + 0.5 * erf   -->    *ph = 0.5 * (1.0-erf)   */
  
  if (x < .477) {
    if (x < XMIN) res = x*p[2]/q[2];  /* erf */
    else {
      xsq = x*x;
      xnum = p[3]*xsq+p[4];
      xden = xsq+q[3];
      for (i=0; i < 3; i++) {
	xnum = xnum*xsq+p[i];
	xden = xden*xsq+q[i];
      }
      res = x*xnum/xden;         /* erf */
    }
  }
  else if (x <= 4.0) {
    xsq  = x*x;
    xnum = p1[7]*x+p1[8];
    xden = x+q1[7];
    for (i=0;i<7;i++) {
      xnum = xnum*x+p1[i];
      xden = xden*x+q1[i];
    }
    res = xnum/xden;
    res = 1.0-res*exp(-xsq);     /* erf */
  }
  else if (x < XLARGE) {
    xsq = x*x;
    xi = 1.0/xsq;
    xnum = p2[4]*xi+p2[5];
    xden = xi+q2[4];
    for (i=0;i<4;i++) {
      xnum = xnum*xi+p2[i];
      xden = xden*xi+q2[i];
    }
    res = (SQRPI+xi*xnum/xden)/x;
    res = 1.0 - res*exp(-xsq);   /* erf */
    
    /* if (res==1.0) printf("\n HEY:  erf = res ==1.0 in  pheev() in invcdfn.c of VAH\n"); */
  }
  else {       
    /* was simply  res = 1.0  in VAH version, but often need to evaluate x > XLARGE */
    /* RLG added this Chebyshev approximation from Numerical Recipes, p. 221. */
    /* Returns the complementary error function erfc(x) with fractional error everywhere less than 1.2 × 10^7. */
    xi = 1.0/(1.0 + 0.5*x); 
    res = xi*exp(-x*x-1.26551223+xi*(1.00002368+xi*(0.37409196+xi*(0.09678418+ xi*(-0.18628806+xi*(0.27886807+xi*(-1.13520398+xi*(1.48851587+ xi*(-0.82215223+xi*0.17087277))))))))); 
    if ( *ph > 0.0 )  res = 2.0-res;
    *ph = 0.5 * res;  /* res is already complement of erf */
    return;           /* return separately here, since all branches above have computed erf(x) */
  }
  
  if ( *ph > 0.0) res = -res;
  
  *ph = 0.5 - .5*res;    /* <-- changed by RLG to use erf complement.  used to be -->  *ph = 0.5 + 0.5 * res; */
}


void phinv(double *ppp)
     /* routine to compute inv of normal cdf, VAH */
     /* rewritten in C by Peter Boatwright        */
{
  
  /*       double precision function phinv(ppp)  */
  /* c===============================================================c  */
  /* c     written by Vassilis Hajivassiliou                         c  */
  /* c     with the aid of Yoosoon Chang.                            c  */
  /* c     version 1.0, March 22, 1992                               c  */
  /* c     version 1.1, July 11, 1992                                c  */
  /* c===============================================================c  */
  /* c     28 mar 86  */
  /* c     12:33 pm, monday, june 30, 1986  */
  /* c*****inverse normal c.d.f. (pheev(ppp)=phinv) using  */
  /* c*****formula 26.2.23 of abramovitz and stegun p.933 10th edition.  */
  /* c*****ppp unchanged on exit. accuracy<4.5e-4  */
  /* c*****  */
  /* c*****written by v.a. hajivassiliou 24sep84  */
  /* c*****  */
  /* c*****uses inverse interpolation to 3rd order so as to  */
  /* c*****raise accuracy-see a&s example 5 p.954  */
  /* c*****  */
  /* c*****modification done 14apr85  */
  /* c*****  */
  /* c*****subprogram(s) used: pheev  */
  /* c*****  */
  /* c+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  */
  double p, t, t2, x0, sfeex0, pheevx0, ph;
  int iflip;
  static double  c0 = 2.515517 ;
  static double  c1 = 0.802853 ;
  static double  c2 = 0.010328 ;
  static double  d1 = 1.432788 ;
  static double  d2 = 0.189269 ;
  static double  d3 = 0.001308 ;
  
  iflip = 0;
  
  p = *ppp;
  
  if (p == 1.0)      *ppp =  18.0;
  else if (p == 0.0) *ppp = -18.0;
  else
    {
      if (p > 0.5)
	{
	  p = 1.0 - *ppp;
	  iflip = 1;
	}
      t2 = log(1.0 / (p*p) );
      t  = sqrt(t2);
      x0 = t- (c0 + c1*t + c2*t2) / (1 + d1*t + d2*t2 + d3*t2*t);
      /* up to now x0<0:   */
      x0 = -x0;
      /* now x0>0 as required by inverse interpolation:  */
      sfeex0  = OSR2PI * exp(-0.5 * x0 * x0);
      pheevx0 = x0;
      pheev( &pheevx0 );
      t =  (p-pheevx0 )/sfeex0;
      t2= t*t;
      ph= x0 + t + x0*t2/2.0 + (2.0*x0*x0 + 1.0)*t*t2/6.0 ;

      /* back to original x0<0, ph<0: */
      if (iflip != 0) ph= -ph;

      *ppp = ph;
    }
}

void invcdfn(xmat,nelem)
     double *xmat, *nelem;
{
  double *p, *pmax;
  pmax = xmat+(int)*nelem;
  for (p = xmat; p < pmax; p++)   phinv( p );
}


/* Next 2 functions:  cdf, inv_cdf  of doubly truncated normal */
/* see  ~/gauss/truncated_normal.gcf  for gauss version.       */

void t_pheev(double *ph, double mu, double sigma, double *lft, double *rgt)
{
  double res,clft,crgt;
  if (*lft > *rgt) { printf("\n\n t_pheev: lft= %8.1e > rgt= %8.1e  aborting...\n\n", *lft, *rgt);  exit(8); }
  clft = (*lft-mu)/sigma;  pheev( &clft );
  crgt = (*rgt-mu)/sigma;  pheev( &crgt );
  res  = (*ph -mu)/sigma;  pheev( &res  );
  *ph  = (res-clft)/(crgt-clft);
  /* *lft = clft;  *rgt = crgt; */    /* <-- is this desired ?? */
}


void t_phinv(double *p, double mu, double sigma, double *lft, double *rgt)
{
  double res,clft,crgt;
  if (*lft > *rgt) { printf("\n\n t_phinv: lft= %8.1e > rgt= %8.1e  aborting...\n\n", *lft, *rgt);  exit(8); }
  clft = (*lft-mu)/sigma;        pheev( &clft );
  crgt = (*rgt-mu)/sigma;        pheev( &crgt );
  if (crgt-clft < 1e-32) { 
    *p = 0.5*(*lft + *rgt);  /* printf("\n\n t_phinv: crgt-clft = %9.1e is tiny ... returning midpoint of %8.3f \n\n", crgt-clft, *p); */
  }
  else { 
    res  = (*p)*(crgt-clft)+clft;  phinv( &res  );
    res  = res*sigma + mu;
    *p   = res*(res>= *lft && res<= *rgt) + *lft *(res< *lft) + *rgt *(res> *rgt);
  }
  *lft = clft;  /* since usually need CDF(lft) and CDF(rgt) */
  *rgt = crgt;
}
