package org.sport.tracker.utils;

import java.util.List;

import org.sport.tracker.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


/**
 * Map overlay. Show record track on map.
 * 
 * @author Waldemar Smirnow
 *
 */
public class WaypointsOverlay extends Overlay {

	/**
	 * Direction width (latitude).
	 */
	public static final int DIRECTION_WIDTH = 0;
	/**
	 * Direction height (longtitude).
	 */
	public static final int DIRECTION_HEIGHT = 1;
	
	/**
	 * Context.
	 */
	Context context;
	/**
	 * List with waypoints data.
	 */
	List<GeoPoint> waypoints;
	/**
	 * Track was drawn (=true).
	 */
	boolean drawn = false;

}
