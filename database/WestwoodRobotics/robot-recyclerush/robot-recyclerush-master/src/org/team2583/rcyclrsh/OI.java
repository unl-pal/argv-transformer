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

package org.team2583.rcyclrsh;

import org.team2583.rcyclrsh.systems.CGEjectBoxes;
import org.team2583.rcyclrsh.systems.CrateJack;
import org.team2583.rcyclrsh.systems.Drawer;
import org.team2583.rcyclrsh.systems.LeftElevator;
import org.team2583.rcyclrsh.systems.LeftTrolley;
import org.team2583.rcyclrsh.systems.RightElevator;
import org.team2583.rcyclrsh.systems.RightTrolley;
import org.team2583.rcyclrsh.systems.Tailgate;

import io.github.robolib.command.Command;
import io.github.robolib.module.hid.HIDAxis;
import io.github.robolib.module.hid.HIDButton;
import io.github.robolib.module.hid.Joystick.JSID;
import io.github.robolib.module.hid.XBoxController;
import io.github.robolib.module.sensor.LimitSwitch;
import io.github.robolib.util.mapper.RobotMap;

/**
 * The Operator Interface Class. Controls Things that interface with the operator.
 *
 * @author Austin Reuland <amreuland@gmail.com>
 */
public final class OI {

    public static final XBoxController JS_DRIVER;
    public static final ArcadeController JS_ACTOR;
    
    public static final HIDAxis AXIS_DRIVER_LEFT_X;
    public static final HIDAxis AXIS_DRIVER_LEFT_Y;
    public static final HIDAxis AXIS_DRIVER_RIGHT_X;
    public static final HIDAxis AXIS_DRIVER_RIGHT_Y;
    
    //Driver Buttons
    public static final HIDButton BTN_SPEED_SCALE;
    
    //Actor Buttons
    public static final HIDButton BTN_EJECT_SEQUENCE;
    public static final HIDButton BTN_CANCEL_SEQUENCE;
    public static final HIDButton BTN_DRAWER_IN;
    public static final HIDButton BTN_DRAWER_OUT;
    public static final HIDButton BTN_DRAWER_GATE_TOGGLE;
    public static final HIDButton BTN_JACK_UP;
    public static final HIDButton BTN_JACK_DOWN;
    public static final HIDButton BTN_RAILROAD_TOGGLE;
    public static final HIDButton BTN_LEFT_ARM_TOGGLE;
    public static final HIDButton BTN_RIGHT_ARM_TOGGLE;
    public static final HIDButton BTN_ARMS_UP;
    public static final HIDButton BTN_ARMS_DOWN;
    
    private static final Command CMD_EJECT_BOXES;
    
    public static final LimitSwitch SWITCH_HAND_LEFT;
    public static final LimitSwitch SWITCH_HAND_RIGHT;
    
    static{
        JS_DRIVER = new XBoxController(JSID.JS0);
        JS_ACTOR = new ArcadeController(JSID.JS1);
        
        AXIS_DRIVER_LEFT_X = JS_DRIVER.getAxisLeftX();
        AXIS_DRIVER_LEFT_Y = JS_DRIVER.getAxisLeftY();
        AXIS_DRIVER_RIGHT_X = JS_DRIVER.getAxisRightX();
        AXIS_DRIVER_RIGHT_Y = JS_DRIVER.getAxisRightY();

        BTN_SPEED_SCALE = JS_DRIVER.getButtonLeftShoulder();
        
        BTN_EJECT_SEQUENCE = JS_ACTOR.getButtonEjectSequence();
        BTN_CANCEL_SEQUENCE = JS_ACTOR.getButtonCancelSequence();
        BTN_DRAWER_IN = JS_ACTOR.getButtonDrawerIn();
        BTN_DRAWER_OUT = JS_ACTOR.getButtonDrawerOut();
        BTN_DRAWER_GATE_TOGGLE = JS_ACTOR.getButtonDrawerGateToggle();
        BTN_JACK_UP = JS_ACTOR.getButtonJackUp();
        BTN_JACK_DOWN = JS_ACTOR.getButtonJackDown();
        BTN_LEFT_ARM_TOGGLE = JS_ACTOR.getButtonLeftArmToggle();
        BTN_RIGHT_ARM_TOGGLE = JS_ACTOR.getButtonRightArmToggle();
        BTN_ARMS_UP = JS_ACTOR.getButtonArmsUp();
        BTN_ARMS_DOWN = JS_ACTOR.getButtonArmsDown();
        BTN_RAILROAD_TOGGLE = JS_ACTOR.getButtonRailroadToggle();
        
        CMD_EJECT_BOXES = new CGEjectBoxes();
        
        SWITCH_HAND_LEFT = RobotMap.getModule("limit_switch_hand_left");
        SWITCH_HAND_RIGHT = RobotMap.getModule("limit_switch_hand_right");
        
        BTN_EJECT_SEQUENCE.runWhenPressed(CMD_EJECT_BOXES);
        BTN_CANCEL_SEQUENCE.cancelWhenPressed(CMD_EJECT_BOXES);
        BTN_DRAWER_IN.runWhileHeld(Drawer.retractContinue());
        BTN_DRAWER_IN.runWhenDoublePressed(Drawer.retract(), 100);
        BTN_DRAWER_OUT.runWhileHeld(Drawer.extendContinue());
        BTN_DRAWER_OUT.runWhenDoublePressed(Drawer.extend(), 100);
        BTN_DRAWER_GATE_TOGGLE.runWhenPressed(Tailgate.toggle());
        BTN_JACK_UP.runWhenPressed(CrateJack.up());
        BTN_JACK_DOWN.runWhenPressed(CrateJack.down());
        BTN_LEFT_ARM_TOGGLE.runWhenPressed(LeftTrolley.toggle());
        BTN_RIGHT_ARM_TOGGLE.runWhenPressed(RightTrolley.toggle());
//        BTN_ARMS_UP.runWhileHeld(new CMDBothArmsUp());
//        BTN_ARMS_DOWN.runWhileHeld(new CMDBothArmsDown());
        BTN_ARMS_UP.runWhileHeld(LeftElevator.upContinue());
//        BTN_ARMS_UP.runWhileHeld(RightElevator.upContinue());
        BTN_ARMS_DOWN.runWhileHeld(LeftElevator.downContinue());
//        BTN_ARMS_DOWN.runWhileHeld(RightElevator.downContinue());
        // BTN_RAILROAD_TOGGLE.runWhenPressed(Railroad.toggle());
    }    
}
