/** filtered and transformed by PAClab */
package edu.iastate.cs228.hw4;

/**
 *  
 * @author Trace Ohrt 
 * 
 *
 */

import org.sosy_lab.sv_benchmarks.Verifier;


/**
 * 
 * This class implements Graham's scan that constructs the convex hull of a finite set of points. 
 *
 */

public class ConvexHull 
{
	// ---------------
	// Data Structures 
	// ---------------

	/**
	 * Generate n random points within the box range [-50, 50] x [-50, 50]. Duplicates are 
	 * allowed. Store the points in the private array points[]. 
	 * 
	 * @param n >= 1; otherwise, exception thrown.  
	 */
	/** PACLab: suitable */
	 public ConvexHull(int n) throws Exception 
	{
		int numPoints = Verifier.nondetInt();
		if (n<1) throw new IllegalArgumentException();			 //obligatory throws

		numPoints=n;
		for(int i=0; i<n ; i++){								//loop creates random point and places it in array
			int x=Verifier.nondetInt()-50;							//random numbers between -50 and 50 for x and y coordinates of point
			int y=Verifier.nondetInt()-50;
		}
	}

	/**
	 * This method carries out Graham's scan in several steps below: 
	 * 
	 *     1) Call the private method lowestPoint() to find the lowest point from the input 
	 *        point set and store it in the variable lowestPoint. 
	 *        
	 *     2) Call the private method setUpScan() to sort all points by polar angle with respect
	 *        to lowestPoint.  After elimination of all duplicates in points[], the points are 
	 *        stored in pointsNoDuplicate[].  Next, for multiple points having the same polar angle 
	 *        with respect to lowestPoint, keep only the one furthest from lowestPoint.  The points
	 *        after the second round of elimination are stored in the array pointsToScan[].  
	 *        
	 *     3) Perform Graham's scan on the points in the array pointsToScan[]. To initialize the 
	 *        scan, push pointsToScan[0] and pointsToScan[1] onto vertexStack.  
	 * 
	 *     4) As the scan terminates, vertexStack holds the vertices of the convex hull.  Pop the 
	 *        vertices out of the stack and add them to the array hullVertices[], starting at index
	 *        numHullVertices - 1, and decreasing the index toward 0.  Set numHullVertices.  
	 *        
	 * Two special cases below must be handled: 
	 * 
	 *     1) The array pointsToScan[] could contain just one point, in which case the convex
	 *        hull is the point itself. 
	 *     
	 *     2) Or it could contain two points, in which case the hull is the line segment 
	 *        connecting them.   	
	 */
	/** PACLab: suitable */
	 public void GrahamScan()
	{ 
		int numHullVertices = Verifier.nondetInt();
		for(int i=3; i<Verifier.nondetInt(); i++){ //graham scan
			if (i!=Verifier.nondetInt()-1 && Verifier.nondetInt()>0){
				}
			}
		
		numHullVertices = Verifier.nondetInt();		//pops stack into array
		for(int i=numHullVertices-1; i>=0; i--){
		}
	}

	// ------------------------------------------------------------
	// toString() and Files for Convex Hull Plotting in Mathematica
	// ------------------------------------------------------------

	/**
	 * Call quickSort() on points[].  After the sorting, duplicates in points[] will appear next 
	 * to each other, with those equal to lowestPoint at the beginning of the array.  
	 * 
	 * Copy the points from points[] into the array pointsNoDuplicate[], eliminating all duplicates. 
	 * Update numDistinctPoints. 
	 * 
	 * Copy the points from pointsNoDuplicate[] into the array pointsToScan[] and eliminate some 
	 * as follows.  If multiple points have the same polar angle, eliminate all but the one that is 
	 * the furthest from lowestPoint.  Update numPointsToScan. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 *
	 */
	/** PACLab: suitable */
	 public void setUpScan()
	{
		;	//temp araylist to store points[] for editing
		for (int i =0; i<Verifier.nondetInt(); i++){
		}
		for (int i=1; i<Verifier.nondetInt(); i++){					//removes duplicates
			if (Verifier.nondetBoolean()){
			}
		}
		for (int i=0; i<Verifier.nondetInt(); i++){
		}

		for (int i=0; i<Verifier.nondetInt(); i++){					//removes close points with same polar angle
			for (int j=1; j<Verifier.nondetInt(); j++){
				if (Verifier.nondetInt()==0){
				}
			}
		}
		for (int i=0; i<Verifier.nondetInt(); i++){
		}


	}


	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	/** PACLab: suitable */
	 private int partition(int first, int last)
	{	
		while (first <= last) {
			//searching number which is greater than pivot, bottom up
			while (Verifier.nondetInt()<0) {
				first++;
			}
			//searching number which is less than pivot, top down
			while (Verifier.nondetInt()>0) {
				last--;
			}

			// swap the values
			if (first <= last) {
				//increment left index and decrement right index
				first++;
				last--;
			}
		}
		return first;
	}	


}
