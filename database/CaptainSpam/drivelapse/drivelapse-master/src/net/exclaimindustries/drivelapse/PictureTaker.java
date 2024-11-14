/**
 * PictureTaker.java
 * Copyright (C)2010 Nicholas Killewald
 * 
 * This file is distributed under the terms of the BSD license.
 * The source package should have a LICENCE file at the toplevel.
 */
package net.exclaimindustries.drivelapse;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.location.Location;
import android.util.Log;

/**
 * @author captainspam
 *
 */
public class PictureTaker {
    private static final String DEBUG_TAG = "PictureTaker";
    
    private String mPackageName;
    private String mDirName;
    private Context mContext;

    /**
     * A SinglePicture is one unit of picture-taking.  This stores the Location
     * of a single picture, writes the picture, and shoves it out to the
     * Annotator.
     * 
     * @author captainspam
     */
    public class SinglePicture implements Camera.PictureCallback {
        
        private Location mLocation;
        private String mDirName;
        private Context mContext;
        
    }
}
