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
import io.github.robolib.command.ContinuousCommand;
import io.github.robolib.command.SingleActionCommand;
import io.github.robolib.command.Subsystem;
import io.github.robolib.module.controller.LimitedController;
import io.github.robolib.util.log.Logger;
import io.github.robolib.util.mapper.RobotMap;

/**
 * 
 * @author Austin Reuland <amreuland@gmail.com>
 */
public final class Drawer extends Subsystem {
    
    static LimitedController m_drawerMotor;
    
    static double m_drawerOutSpeed;
    static double m_drawerInSpeed;
    static boolean m_drawerExtended;
    
    static final Drawer m_instance = new Drawer();
    
    private class CMDExtendDrawer extends Command {
    }
    
    private class CMDRetractDrawer extends Command {
    }
    
    private final class CMDExtendDrawerContinue extends ContinuousCommand {
    }
    
    private final class CMDRetractDrawerContinue extends ContinuousCommand {
    }
    
    private class CMDToggleDrawer extends Command {
        double dir;
    }
    
    private class CMDStopDrawer extends SingleActionCommand {
    }
}

