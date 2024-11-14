package org.sport.tracker;

import java.util.ArrayList;
import java.util.List;

import org.sport.tracker.utils.Waypoint;
import org.sport.tracker.utils.WaypointDBHelper;
import org.sport.tracker.utils.WaypointsOverlay;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

/**
 * Map activity to show record waypoints on map.
 * 
 * @author Waldemar Smirnow
 *
 */
public class MapUI extends MapActivity {

	/**
	 * Record id.
	 */
	long recordId;
}
