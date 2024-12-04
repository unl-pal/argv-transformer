package org.sport.tracker.utils;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.text.TextUtils;

/**
 * Waypoints database helper. Provide Waypoint CRUD functions.
 * 
 * @author Waldemar Smirnow
 *
 */
public class WaypointDBHelper extends SQLiteOpenHelper {

	/**
	 * Static waypoints table name (should be used for constructor).
	 */
	public static final String WAYPOINT_TABLE_NAME = "waypoints";
	
	/**
	 * Column name for waypoint id.
	 */
	public static final String KEY_ID = "_id";
	/**
	 * Column name for record id.
	 */
	public static final String KEY_RECORD_ID = "recordID";
	/**
	 * Column name for waypoint latitude.
	 */
	public static final String KEY_LATITUDE = "latitude";
	/**
	 * Column name for waypoint longtitude.
	 */
	public static final String KEY_LONGTITUDE = "longtitude";
	/**
	 * Column name for waypoint altitude.
	 */
	public static final String KEY_ALTITUDE = "altitude";
	/**
	 * Column name for waypoint accuracy.
	 */
	public static final String KEY_ACCURACY = "accuracy";
	/**
	 * Column name for waypoint time.
	 */
	public static final String KEY_TIME = "time";
	/**
	 * Column name for waypoint speed.
	 */
	public static final String KEY_SPEED = "speed";
	
	/**
	 * Waypoints table name.
	 */
	final String TABLE_NAME;

}
