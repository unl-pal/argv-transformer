package com.jsyn.examples;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JComboBox;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceFactory;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.scope.AudioScope;
import com.jsyn.swing.JAppletFrame;
import com.jsyn.unitgen.ChannelIn;
import com.jsyn.unitgen.PassThrough;

/**
 * Two channel oscilloscope that demonstrates the use of audio input.
 * 
 * @author Phil Burk (C) 2012 Mobileer Inc
 * 
 */
public class DualOscilloscope extends JApplet
{
	private static final long serialVersionUID = -2704222221111608377L;
	private Synthesizer synth;
	private ChannelIn channel1;
	private ChannelIn channel2;
	private PassThrough pass1;
	private PassThrough pass2;
	private AudioScope scope;
	private AudioDeviceManager audioManager;
	private int defaultInputId;
	private ArrayList<String> deviceNames = new ArrayList<String>();
	private ArrayList<Integer> deviceMaxInputs = new ArrayList<Integer>();
	private ArrayList<Integer> deviceIds = new ArrayList<Integer>();
	private int defaultSelection;
	private JComboBox deviceComboBox;

}
