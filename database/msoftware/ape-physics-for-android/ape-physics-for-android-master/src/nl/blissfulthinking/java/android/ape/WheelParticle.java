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
import java.util.ArrayList;

import nl.blissfulthinking.java.android.apeforandroid.FP;
	
	/**
	 * A particle that simulates the behavior of a wheel 
	 */ 
	public class WheelParticle extends CircleParticle  {
	
		private final RimParticle rp;
		private final Vector tan;	
		private final Vector normSlip;
		
		//MvdA TODO make this fast
		private final ArrayList _edgePositions = new ArrayList();
		private final ArrayList _edgeParticles = new ArrayList();
		private int _traction;
	}


