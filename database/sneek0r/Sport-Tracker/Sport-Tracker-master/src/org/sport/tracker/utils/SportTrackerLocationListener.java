package org.sport.tracker.utils;

import org.sport.tracker.RecordUI;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * LocationListener. Create an record and fill it with Waypoint data from android location service.
 * 
 * @author Waldemar Smirnow
 *
 */
public class SportTrackerLocationListener implements LocationListener {
	
	/**
	 * Context.
	 */
	Context context;
	/**
	 * Record.
	 */
	public Record record;

}
