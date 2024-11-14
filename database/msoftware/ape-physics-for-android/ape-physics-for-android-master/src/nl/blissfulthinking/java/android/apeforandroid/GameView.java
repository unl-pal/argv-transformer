package nl.blissfulthinking.java.android.apeforandroid;

import nl.blissfulthinking.java.android.ape.APEngine;
import nl.blissfulthinking.java.android.ape.CircleParticle;
import nl.blissfulthinking.java.android.ape.RectangleParticle;
import nl.blissfulthinking.java.android.ape.Vector;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * View that draws, takes keystrokes, etc. for a simple LunarLander game.
 * 
 * Has a mode which RUNNING, PAUSED, etc. Has a x, y, dx, dy, ... capturing the
 * current ship physics. All x/y etc. are measured with (0,0) at the lower left.
 * updatePhysics() advances the physics based on realtime. draw() renders the
 * ship, and does an invalidate() to prompt another draw() as soon as possible
 * by the system.
 */
class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    class GameThread extends Thread {
        /*
         * State-tracking constants
         */
        public static final int STATE_LOSE = 1;
        public static final int STATE_PAUSE = 2;
        public static final int STATE_READY = 3;
        public static final int STATE_RUNNING = 4;
        public static final int STATE_WIN = 5;
        
//      private float x;
//      private float y;
     
        private int fingerX = 0;
        private int fingerY = 0;
        
//        private int speedX = 0;
//        private int speedY = 0;
        
//        private static final int SPEED = 100;
        private boolean dRight;
        private boolean dLeft;
        private boolean dUp;
        private boolean dDown;
//        
//        private int mCanvasWidth;
//        private int mCanvasHeight;
//
//        private long mLastTime;
//        private Bitmap mSnowflake;
        
        private PhysicsWorld physicsWorld = new PhysicsWorld();
        
         /** Message handler used by thread to post stuff back to the GameView */
//      private Handler mHandler;

         /** The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN */
        private int mMode;
        /** Indicate whether the surface has been created & is ready to draw */
        private boolean mRun = false;
        /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;
//		private int type1won;
//		private int type2won;
    }

    /** Handle to the application context, used to e.g. fetch Drawables. */
//    private Context mContext;

    /** The thread that actually draws the animation */
    private GameThread thread;
    
    public static int width;
    public static int height;
    
	public float pitch;
	public float roll;
}
