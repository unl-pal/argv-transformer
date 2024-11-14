/*
 * Created by Austin Reuland <amreuland@gmail.com>
 * Copyright (c) 2015 Westwood Robotics <code.westwoodrobotics@gmail.com>.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 */

package org.team2583.rcyclrsh;

import io.github.robolib.module.LCD2004;
import io.github.robolib.module.RoboRIO;
import io.github.robolib.module.iface.I2C.Port;
import io.github.robolib.module.sensor.HMC5883L;
import io.github.robolib.module.sensor.mpu6050.MPU6050;
import io.github.robolib.util.Timer;

/**
 * 
 *
 * @author Austin Reuland <amreuland@gmail.com>
 */
public class LCDManager {
    
    private LCD2004 lcd0;
//    private Object m_sem;
    private static volatile boolean m_run = true;
    private Thread m_thread;
    private HMC5883L mag;
    private MPU6050 mpu;
//    private ArdEx ad0;

}
