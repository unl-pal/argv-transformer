/** 
 * Test Envelope using Java Audio Synthesizer
 * Trigger attack or release portion.
 *
 * @author (C) 1997 Phil Burk, All Rights Reserved
 */

package com.jsyn.examples;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.swing.EnvelopeEditorBox;
import com.jsyn.swing.EnvelopeEditorPanel;
import com.jsyn.swing.EnvelopePoints;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;

public class EditEnvelope1 extends JApplet
{
	private Synthesizer synth;
	private UnitOscillator osc;
	private LineOut lineOut;
	private SegmentedEnvelope envelope;
	private VariableRateDataReader envelopePlayer;

	final int MAX_FRAMES = 16;
	JButton hitme;
	JButton attackButton;
	JButton releaseButton;
	private EnvelopeEditorPanel envEditor;
	private EnvelopePoints points;
}
