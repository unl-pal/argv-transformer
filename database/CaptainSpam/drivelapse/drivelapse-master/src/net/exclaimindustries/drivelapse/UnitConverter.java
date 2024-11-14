/**
 * Copyright (C)2010 Nicholas Killewald
 * 
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENCE file at the toplevel.
 */
package net.exclaimindustries.drivelapse;

import java.text.DecimalFormat;

import android.content.Context;
//import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

/**
 * This is a simple utility class which helps with unit conversions and 
 * formatting.
 * 
 * @author Nicholas Killewald
 */
public class UnitConverter {
    /** The number of feet per meter. */
    public static final double FEET_PER_METER = 3.2808399;
    /** The number of feet per mile. */
    public static final int FEET_PER_MILE = 5280;
    
    /** Output should be short, with fewer decimal places. */
    public static final int OUTPUT_SHORT = 0;
    /** Output should be long, with more decimal places. */
    public static final int OUTPUT_LONG = 1;
    /** Output should be even longer, with even more decimal places. */
    public static final int OUTPUT_DETAILED = 2;
    
    protected static final DecimalFormat SHORT_FORMAT = new DecimalFormat("###.000");
    protected static final DecimalFormat LONG_FORMAT = new DecimalFormat("###.00000");
    protected static final DecimalFormat DETAIL_FORMAT = new DecimalFormat("###.00000000");
    
    protected static final DecimalFormat SHORT_SECONDS_FORMAT = new DecimalFormat("###.00");
    protected static final DecimalFormat LONG_SECONDS_FORMAT = new DecimalFormat("###.0000");
    
    private static final String DEBUG_TAG = "UnitConverter";
    
//    /**
//     * Grab the current coordinate unit preference.
//     * 
//     * @param c Context from whence the preferences arise
//     * @return "Degrees", "Minutes", or "Seconds"
//     */
//    public static String getCoordUnitPreference(Context c) {
//        // Units GO!!!
//        SharedPreferences prefs = c.getSharedPreferences(
//                GHDConstants.PREFS_BASE, 0);
//        return prefs.getString(GHDConstants.PREF_COORD_UNITS, "Degrees");
//    }
}
