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

import org.team2583.rcyclrsh.OI;

import io.github.robolib.command.Command;
import io.github.robolib.command.ContinuousCommand;
import io.github.robolib.command.SingleActionCommand;
import io.github.robolib.command.Subsystem;
import io.github.robolib.module.actuator.DriveBase;
import io.github.robolib.module.actuator.Talon;
import io.github.robolib.util.mapper.RobotMap;

/**
 *
 * @author Austin Reuland <amreuland@gmail.com>
 */
public final class Drivetrain extends Subsystem{
    
    /**
     * DriveMode Class
     * Used to determine the type of driving we are doing
     */
    private final static class DriveMode {
        public final Command m_command;
        public final String m_name;
    }
    
    private static Talon m_motorFrontLeft;
    private static Talon m_motorFrontRight;
    private static Talon m_motorRearLeft;
    private static Talon m_motorRearRight;
    
    public static DriveMode MECANUM;
    public static DriveMode ARCADE;
    public static DriveMode TANK;
    
    static DriveBase m_driveBase;

    private static final Drivetrain m_instance = new Drivetrain();

    private final class CMDMecanumDrive extends ContinuousCommand {
        final double xMove, yMove, rotMove;
    }
    
    private final class CMDChangeDrivemode extends SingleActionCommand {
        private final DriveMode m_mode;
    }
    
    private final class ArcadeMode extends ContinuousCommand {
    }

    private final class MecanumMode extends ContinuousCommand {
        private double slow = 1;
        private boolean pressed = false;

    }
    
    private final class TankMode extends ContinuousCommand {
    }
}

