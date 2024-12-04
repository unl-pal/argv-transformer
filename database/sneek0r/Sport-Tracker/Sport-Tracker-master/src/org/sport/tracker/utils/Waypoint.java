package org.sport.tracker.utils;

import java.util.ArrayList;
import java.util.List;

import org.sport.tracker.SportTrackerProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

/**
 * Waypoint ORM to database.
 * 
 * @author Waldemar Smirnow
 *
 */
public class Waypoint {

	/**
	 * Record id.
	 */
	public final long recordId;
	/**
	 * Altitude.
	 */
	public final double altitude;
	/**
	 * Latitude.
	 */
	public final double latitude;
	/**
	 * Longtitude.
	 */
	public final double longtitude;
	/**
	 * Speed.
	 */
	public final float speed;
	/**
	 * Accuracy.
	 */
	public final float accuracy;
	/**
	 * Time.
	 */
	public final long time;
	/**
	 * Waypoint id.
	 */
	public long waypointId;
	/**
	 * Waypoint url.
	 */
	public Uri waypointUrl;
}
