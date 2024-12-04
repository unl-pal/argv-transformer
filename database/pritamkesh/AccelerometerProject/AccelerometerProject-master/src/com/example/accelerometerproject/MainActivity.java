package com.example.accelerometerproject;

import android.app.Activity;
	import android.content.Context;
import android.hardware.Sensor;
	import android.hardware.SensorEvent;
	import android.hardware.SensorEventListener;
	import android.hardware.SensorManager;
	import android.os.Bundle;
	import android.os.Vibrator;
import android.widget.TextView;
	 
	public class MainActivity extends Activity implements SensorEventListener {
	 
	    private float lastX, lastY, lastZ;
 
    private SensorManager sensorManager;
	    private Sensor accelerometer;
	 
	    private float deltaXMax = 0;
	    private float deltaYMax = 0;
	    private float deltaZMax = 0;
	 
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;
 
   private float vibrateThreshold = 0;
 
    private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;
	 
    public Vibrator v;
	}

