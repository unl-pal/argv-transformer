package org.sport.tracker.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sport.tracker.SportTrackerProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

/**
 * Record ORM to database.
 * 
 * @author Waldemar Smirnow
 *
 */
public class Record {
	/**
	 * Context.
	 */
	Context context;
	/**
	 * Record Url.
	 */
	public Uri recordUrl;
	/**
	 * Record ID.
	 */
	public long recordId = 0;
	/**
	 * Record profile.
	 */
	public final String profile;
	/**
	 * Record start time (millis).
	 */
	public long startTime;
	/**
	 * Record end time (millis).
	 */
	public long endTime;
	/**
	 * Record average speed (m/s).
	 */
	public float averageSpeed = 0f;
	/**
	 * Record distance (m).
	 */
	public float distance = 0f;
	/**
	 * Record waypoints.
	 */
	List<Waypoint> waypoints;
	/**
	 * Record comment.
	 */
	public String comment = "";
	/**
	 * Last location, need to compute distance.
	 */
	Location lastLoc = null;

}
