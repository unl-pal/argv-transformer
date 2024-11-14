package net.exclaimindustries.drivelapse;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
//import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.hardware.Camera;

public class DriveLapse extends Activity implements LocationListener, SurfaceHolder.Callback {
    private static final String DEBUG_TAG = "DriveLapse";
    
    private static final String SAVE_STATE = "State";
    private static final String SAVE_ACTIVE_DATE = "ActiveDate";
    
    /** The recording is stopped entirely.  Display the Go button. */
    private static final int STATE_STOP = 0;
    /** We're recording!  Display the Pause button. */
    private static final int STATE_RECORD = 1;
    /** We've paused.  Display both the Go and Stop buttons. */
    private static final int STATE_PAUSE = 2;
    
    private int mLastState;

    private LocationManager mLocationManager;
    
    private Button mGoButton;
    private Button mStopButton;
    private Button mPauseButton;
    
    private TextView mTextView;
    
    private int mCount;
    
    private Location mLastLoc;
    
    private WakeLock mWakeLock;
    
    private Camera mCamera;
    
    private PictureTaker mPictureTaker;
    
    private ScrollView mScroller;
    private SurfaceView mSurface;
    
    private long mActiveDate = -1;
}