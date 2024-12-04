/*
APE (Actionscript Physics Engine) is an AS3 open source 2D physics engine
Copyright 2006, Alec Cove 

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Contact: nl.blissfulthinking.java.android.ape@cove.org

Converted to Java by Theo Galanakis theo.galanakis@hotmail.com

Optimized for Android by Michiel van den Anker michiel.van.den.anker@gmail.com

*/
package nl.blissfulthinking.java.android.ape;

import nl.blissfulthinking.java.android.apeforandroid.FP;

	
	public class Vector {
		
		public int x;
		public int y;
		
		private static final int[] tmp = new int[2];
		
//		public static int creationCount = 0;
//		public static int poolCreationCount = 0;
//		public static int poolUnavailableCount = 0;
//		public static int poolReleaseCount = 0;
		
		public Vector next = null;
		public static Vector available = null;
//		public static Queue<Vector> pool = new LinkedList<Vector>();
	}
