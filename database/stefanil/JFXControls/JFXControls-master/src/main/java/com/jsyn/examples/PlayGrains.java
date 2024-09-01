package com.jsyn.examples;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.ContinuousRamp;
import com.jsyn.unitgen.GrainFarm;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SampleGrainFarm;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

/**
 * Play with Granular Synthesis tools.
 * 
 * @author Phil Burk (C) 2011 Mobileer Inc
 * 
 */
public class PlayGrains extends JApplet
{
	private static final long serialVersionUID = -8315903842197137926L;
	private Synthesizer synth;
	private LineOut lineOut;
	private AudioScope scope;
	private GrainFarm grainFarm;
	private ContinuousRamp ramp;
	private static final int NUM_GRAINS = 32;
	private FloatSample sample;
	private WaveRecorder recorder;

	private static final boolean useSample = false;
	private final static boolean useRecorder = false;

	// File sampleFile = new File( "samples/instructions.wav" );
	File sampleFile = new File(
			"/Users/phil/Work/jsyn/guitar100/Guitar100_Ocean_1#02.aif" );

}
