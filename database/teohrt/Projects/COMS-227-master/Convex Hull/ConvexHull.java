package edu.iastate.cs228.hw4;

/**
 *  
 * @author Trace Ohrt 
 * 
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException; 
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException; 
import java.io.PrintWriter;
import java.util.Random; 
import java.util.Scanner;


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
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates may appear. 
	 */
	private ArrayList<Point> points2;
	private Point[] points;    
	private int numPoints;            // size of points[]


	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by the private method lowestPoint(). 
	 */
	private Point lowestPoint; 


	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 */
	private Point[] pointsNoDuplicate; 
	private int numDistinctPoints;    // size of pointsNoDuplicate[]


	/**
	 * Points on which Graham's scan is performed.  They are copied from pointsNoDuplicate[] 
	 * with some points removed.  More specifically, if multiple points from the array 
	 * pointsNoDuplicate[] have the same polar angle with respect to lowestPoint, only the one 
	 * furthest away from lowestPoint is included. 
	 */
	private Point[] pointsToScan; 
	private int numPointsToScan;     // size of pointsToScan[]


	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	private Point[] hullVertices;
	private int numHullVertices;     // number of vertices on the convex hull


	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the points 
	 * scanned so far.  At the end of the scan, it stores the hull vertices in the 
	 * counterclockwise order. 
	 */
	private PureStack<Point> vertexStack = new ArrayBasedStack();  

	/**
	 * PointComparator
	 */
	private PointComparator pc;
	private Point pivot;

	// ------------
	// Constructors
	// ------------


	/**
	 * Generate n random points within the box range [-50, 50] x [-50, 50]. Duplicates are 
	 * allowed. Store the points in the private array points[]. 
	 * 
	 * @param n >= 1; otherwise, exception thrown.  
	 */
	public ConvexHull(int n) throws IllegalArgumentException 
	{
		if (n<1) throw new IllegalArgumentException();			 //obligatory throws

		numPoints=n;
		Random rand = new Random();								 //random for point creation	
		points = new Point[numPoints]; 							//array initialization

		for(int i=0; i<n ; i++){								//loop creates random point and places it in array
			int x=rand.nextInt(101)-50;							//random numbers between -50 and 50 for x and y coordinates of point
			int y=rand.nextInt(101)-50;
			Point p = new Point(x, y);
			points[i]=p;
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
	public void GrahamScan()
	{ 
		lowestPoint(); 						//step one
		setUpScan();						//step two

		PointComparator GrahamScanpc; 		//comparator for scan
		Point previous;

		vertexStack.push(pointsToScan[0]);	//step 3 initializes scan
		vertexStack.push(pointsToScan[1]);

		for(int i=3; i<pointsToScan.length; i++){ //graham scan
			previous=vertexStack.peek();
			GrahamScanpc = new PointComparator(previous);
			vertexStack.push(pointsToScan[i]);
				if (i!=pointsToScan.length-1 && GrahamScanpc.comparePolarAngle(pointsToScan[i], pointsToScan[i+1])>0){ //if left turn
					vertexStack.pop();
				}
			}
		
		numHullVertices = vertexStack.size();		//pops stack into array
		hullVertices = new Point[numHullVertices];
		for(int i=numHullVertices-1; i>=0; i--){
			hullVertices[i]=vertexStack.peek();
			vertexStack.pop();
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
	public void setUpScan()
	{
		quickSort();

		ArrayList <Point> temp = new ArrayList<Point>();;	//temp araylist to store points[] for editing
		for (int i =0; i<points.length; i++){				//populates new list
			temp.add(points[i]);
		}
		for (int i=1; i<temp.size(); i++){					//removes duplicates
			if (temp.get(i).equals(temp.get(i-1))){
				temp.remove(i);
			}
		}
		pointsNoDuplicate = new Point[temp.size()];			//initializes pointsNoDuplicate
		for (int i=0; i<pointsNoDuplicate.length; i++){		//populates it
			pointsNoDuplicate[i]=temp.get(i);
		}

		for (int i=0; i<temp.size(); i++){					//removes close points with same polar angle
			for (int j=1; j<temp.size(); j++){
				if (pc.comparePolarAngle(temp.get(i), temp.get(j))==0){
					switch(pc.compareDistance(temp.get(i), temp.get(j))){
					case -1:{ temp.remove(i);
					break;}
					case 1:{ temp.remove(j);
					break;
					}
					}
				}
			}
		}
		pointsToScan = new Point[temp.size()];			//initializes pointsToScan
		for (int i=0; i<pointsToScan.length; i++){		//populates it
			pointsToScan[i]=temp.get(i);
		}


	}


	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{	
		pivot = points[first]; // taking first element as pivot

		while (first <= last) {
			//searching number which is greater than pivot, bottom up
			while (pc.compare(points[first], pivot)<0) {
				first++;
			}
			//searching number which is less than pivot, top down
			while (pc.compare(points[last], pivot)>0) {
				last--;
			}

			// swap the values
			if (first <= last) {
				Point tmp = points[first];
				points[first] = points[last];
				points[last] = tmp;

				//increment left index and decrement right index
				first++;
				last--;
			}
		}
		return first;
	}	


}
