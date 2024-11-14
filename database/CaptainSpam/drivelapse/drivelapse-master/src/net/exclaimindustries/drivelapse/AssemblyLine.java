/**
 * AssemblyLine.java
 * Copyright (C)2010 Nicholas Killewald
 * 
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENCE file at the toplevel.
 */
package net.exclaimindustries.drivelapse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * The AssemblyLine is where a series of picture go to get themselves all
 * processed up.  It has an ordered series of Stations that process
 * WorkOrders given to it by an OrderProducer.
 * 
 * @author Nicholas Killewald
 */
public class AssemblyLine extends IntentService {

    private static final String DEBUG_TAG = "AssemblyLine";
    
    public static final String WORK_ORDER = "net.exclaimindustries.drivelapse.workorder";
    
    /**
     * A WorkOrder is the file and GPS location of a single picture to be worked
     * on.  Presumably, the file won't go away as we go along.  A WorkOrder can
     * also contain a special command, such as one to indicate the end of the
     * queue entirely.
     * 
     * @author Nicholas Killewald
     */
    public static class WorkOrder implements Parcelable {
        protected String mFileLocation;
        protected Location mGpsLocation;
        protected Canvas mWorkingCanvas;
        protected Bundle mExtraData;
        
        public static final Parcelable.Creator<WorkOrder> CREATOR = new Parcelable.Creator<WorkOrder>() {
            public WorkOrder createFromParcel(Parcel in) {
                return new WorkOrder(in);
            }

            public WorkOrder[] newArray(int size) {
                return new WorkOrder[size];
            }
        };
    }
    
    /**
     * A Station is one discrete modification instruction to be performed on a
     * WorkOrder.  Such instructions can be, for instance, annotation, exif
     * handling, movie construction, etc, etc.  Note that it is expected that
     * the image will stay on disk in the same spot as it was to begin with.
     * 
     * @author Nicholas Killewald
     */
    public abstract static class Station {
    }
}
