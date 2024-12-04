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
 * Record database helper. Provide Record CRUD functions.
 * 
 * @author Waldemar Smirnow
 *
 */
public class RecordDBHelper extends SQLiteOpenHelper {

	/**
	 * Static records table name (should be used for constructor).
	 */
	public static final String RECORDS_TABLE_NAME = "records";
	/**
	 * Column name for record id.
	 */
	public static final String KEY_ID = "_id";
	/**
	 * Column name for record profile.
	 */
	public static final String KEY_PROFILE = "profile";
	/**
	 * Column name for record start time.
	 */
	public static final String KEY_START_TIME = "starttime";
	/**
	 * Column name for record end time.
	 */
	public static final String KEY_END_TIME = "endtime";
	/**
	 * Column name for record distance.
	 */
	public static final String KEY_DISTANCE = "distance";
	/**
	 * Column name for record average speed.
	 */
	public static final String KEY_AVERAGE_SPEED = "averagespeed";
	/**
	 * Column name for record comment.
	 */
	public static final String KEY_COMMENT = "comment";
	
	/**
	 * Records table name.
	 */
	final String TABLE_NAME;
}
