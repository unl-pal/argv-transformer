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
import io.github.robolib.command.CommandGroup;
import io.github.robolib.command.ContinuousCommand;
import io.github.robolib.command.SingleActionCommand;
import io.github.robolib.command.Subsystem;
import io.github.robolib.module.actuator.CANJaguar;
import io.github.robolib.util.mapper.RobotMap;

/**
 *
 * @author Austin Reuland <amreuland@gmail.com>
 */
public final class CrateJack extends Subsystem {
    
    private static CANJaguar m_motorLeft;
    private static CANJaguar m_motorRight;
    
    static double lift_speed;
    
    static boolean m_atTop;
    
    static final CrateJack m_instance = new CrateJack();

    /**
     * 
     * @author Austin Reuland <amreuland@gmail.com>
     */
   private class CMDLiftCrates extends Command {
   }
   
   private class CMDDropCrates extends Command {
   }
   
   private final class CMDLiftCratesContinue extends ContinuousCommand {
   }
   
   private final class CMDDropCratesContinue extends ContinuousCommand {
   }
   
   private class CMDToggleCrateJack extends Command {
       double dir;
   }
   
   private class CGCycleCrateJack extends CommandGroup {
   }
   
   private class CMDStopLift extends SingleActionCommand {
   }

}

