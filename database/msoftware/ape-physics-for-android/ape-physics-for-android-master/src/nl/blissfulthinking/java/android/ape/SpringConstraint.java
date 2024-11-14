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
	 * A Spring-like constraint that connects two particles
	 */
	public class SpringConstraint extends AbstractConstraint {
		
		private final AbstractParticle p1;
		private final AbstractParticle p2;
		
		private int restLen;

		private final int[] delta = new int[2];
		private final int[] center =  new int[2];
		private final int[] dmd =  new int[2];
		private final int[] tmp1 = new int[2];
		
		private int deltaLength;
		
		private int collisionRectWidth;
		private int collisionRectScale;
		public boolean collidable = false;
		private SpringConstraintParticle collisionRect;
	
//		/**
//		 * if the two particles are at the same location warn the user
//		 */
//		private void checkParticlesLocation() {
//			if (p1.curr.x == p2.curr.x && p1.curr.y == p2.curr.y) {
//				throw new Error("The two particles specified for a SpringContraint can't be at the same location");
//			}
//		}
	}
