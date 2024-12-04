package io.bunnies.fractalmaps.touch;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FractalTouchHandler implements IFractalTouchHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(FractalTouchHandler.class);

    private IFractalTouchDelegate delegate;
    private Context context;

    // Dragging/scaling control variables
    private float dragLastX;
    private float dragLastY;
    protected int dragID = -1;
    private boolean currentlyDragging = false;
    private boolean currentlyScaling = false;

    private ScaleGestureDetector gestureDetector;

    private float totalDragX = 0;
    private float totalDragY = 0;
    private float currentScaleFactor = 0;

    private static final float MAX_MOVEMENT_JITTER = 5f;
}
