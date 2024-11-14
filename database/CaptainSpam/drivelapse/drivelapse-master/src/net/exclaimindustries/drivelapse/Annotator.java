/**
 * Annotator.java
 * Copyright (C)2010 Nicholas Killewald
 * 
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENCE file at the toplevel.
 */
package net.exclaimindustries.drivelapse;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import net.exclaimindustries.drivelapse.AssemblyLine.WorkOrder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

/**
 * The Annotator does annotation.  Obviously.  It kicks off a thread that does
 * all modifications to the image as need be, mostly involving putting the
 * various info boxes on it.
 * 
 * @author captainspam
 */
public class Annotator extends AssemblyLine.Station {
    private static final String DEBUG_TAG = "Annotator";
    
    private Paint mBackgroundPaint;
    private Paint mTextPaint;
    
    private Geocoder mGeocoder;
    private Context mContext;
    
    // The following three are defined once the first picture comes in.
    /** How tall a box is. */
    private int mBoxHeight;
    /** Amount of padding in the box itself. */
    private int mBoxPadding;
    /** Distance between the box and the side and bottom of the pic. */
    private int mBoxMargin;
}
