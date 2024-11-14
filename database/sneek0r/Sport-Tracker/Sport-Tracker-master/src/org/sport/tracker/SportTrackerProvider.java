package org.sport.tracker;

import org.sport.tracker.utils.RecordDBHelper;
import org.sport.tracker.utils.WaypointDBHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * ContentProvider for records and waypoints.
 * 
 * @author Waldemar Smirnow
 *
 */
public class SportTrackerProvider extends ContentProvider {

	/**
	 * Record content uri.
	 */
	public static final Uri RECORD_CONTENT_URI = Uri.parse("content://org.sport.tracker/records");
	/**
	 * Waypoint content uri.
	 */
	public static final Uri WAYPOINT_CONTENT_URI = Uri.parse("content://org.sport.tracker/waypoints");
	/**
	 * Records id for uri matcher.
	 */
	static final int RECORDS = 0;
	/**
	 * Record id for uri matcher.
	 */
	static final int RECORD_ID = 1;
	/**
	 * Waypoints id for uri matcher.
	 */
	static final int WAYPOINTS = 2;
	/**
	 * Waypoint id for uri matcher.
	 */
	static final int WAYPOINT_ID = 3;
	/**
	 * Uri matcher.
	 */
	static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("org.sport.tracker", "records", RECORDS);
		uriMatcher.addURI("org.sport.tracker", "records/#", RECORD_ID);
		uriMatcher.addURI("org.sport.tracker", "waypoints/#", WAYPOINTS);
		uriMatcher.addURI("org.sport.tracker", "waypoints/#/#", WAYPOINT_ID);
	}

	/**
	 * Record database helper.
	 */
	private RecordDBHelper recordDbHelper;
	/**
	 * Waypoint database helper.
	 */
	private WaypointDBHelper waypointDbHelber;
}
