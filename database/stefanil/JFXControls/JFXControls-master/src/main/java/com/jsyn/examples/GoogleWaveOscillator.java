package com.jsyn.examples;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitOscillator;

public class GoogleWaveOscillator extends UnitOscillator
{
	public UnitInputPort variance;
	private double phaseIncrement = 0.1;
	private double previousY;
	private double randomAmplitude = 0.0;
}