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
import nl.blissfulthinking.java.android.apeforandroid.Paints;
import android.graphics.Canvas;

	/**
	 * A rectangular shaped particle. 
	 */ 
	public class RectangleParticle extends AbstractParticle {
		
		public final int[] currTemp = new int[2];
		public final Vector[] cornerPositions = new Vector[4];
		
		//MvdA TODO reestablish cornerParticle functionality
//		public final AbstractParticle[] cornerParticles = new AbstractParticle[4];
		
		public final int[] extents = new int[2];
//		
//		public final Vector[] axes = new Vector[2];
//		
		public final int[] axes0 = new int[2];
		public final int[] axes1 = new int[2];
		private int rotation;

		private boolean show;

	}
