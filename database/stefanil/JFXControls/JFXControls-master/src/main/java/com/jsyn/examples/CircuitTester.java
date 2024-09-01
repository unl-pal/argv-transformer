package com.jsyn.examples;

import java.awt.BorderLayout;

import javax.swing.JApplet;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.swing.SoundTweaker;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.UnitSource;

/**
 * Listen to a circuit while tweaking it knobs. Show output in a scope.
 * 
 * @author Phil Burk (C) 2012 Mobileer Inc
 * 
 */
public class CircuitTester extends JApplet
{
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	private LineOut lineOut;
	private SoundTweaker tweaker;
	private UnitSource unitSource;
	private AudioScope scope;

}
