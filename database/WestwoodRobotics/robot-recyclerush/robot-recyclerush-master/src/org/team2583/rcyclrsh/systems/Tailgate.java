/*
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

package org.team2583.rcyclrsh.systems;

import io.github.robolib.command.Command;
import io.github.robolib.command.SingleActionCommand;
import io.github.robolib.command.Subsystem;
import io.github.robolib.module.actuator.Solenoid;
import io.github.robolib.util.mapper.RobotMap;

/**
 *
 * @author Austin Reuland <amreuland@gmail.com>
 */
public final class Tailgate extends Subsystem {

    static Solenoid m_tailGate;

    static boolean m_tailgateDown;
    
    static final Tailgate m_instance = new Tailgate();

    /**
     *
     * @author Austin Reuland <amreuland@gmail.com>
     */
    private final class CMDLowerTailgate extends SingleActionCommand {
    }
    
    private final class CMDRaiseTailgate extends SingleActionCommand {
    }
    
    private final class CMDToggleTailgate extends SingleActionCommand {
    }
}

