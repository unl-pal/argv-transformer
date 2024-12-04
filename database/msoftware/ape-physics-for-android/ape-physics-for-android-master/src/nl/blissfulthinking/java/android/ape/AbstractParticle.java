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
import android.graphics.Canvas;
	 	
	/**
	 * The abstract base class for all particles.
	 * 
	 * <p>
	 * You should not instantiate this class directly -- instead use one of the subclasses.
	 * </p>
	 */
	public abstract class AbstractParticle {
		
		
//		public final Vector curr;
		public final int[] curr = new int[2];
		
//		public final Vector prev;
		public final int[] prev = new int[2];
		
		
		public boolean isColliding;
		
		public final Interval interval;

		private final int[] forces = new int[2];
		
		private final int[] temp = new int[2];
		
		public AbstractParticle next = null;
		
		public int kfr;
		public int mass;
		public int invMass;
		public boolean fixed;
		public boolean visible;
		public int friction;
		public boolean collidable;
		private final Collision collision;
		
		private static final int[] nv = new int[2];
		private static final int[] vel = new int[2];
	}	
