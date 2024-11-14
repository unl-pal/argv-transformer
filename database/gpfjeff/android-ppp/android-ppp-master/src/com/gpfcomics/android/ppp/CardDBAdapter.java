/* CardDBAdapter.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This class provides an interface between the PPP card set database and the rest
 * of the application.  All database interactions should and must go through this
 * class.  The common shared instance of the DB adapter will be "owned" by the
 * overall application class, PPPApplication.
 * 
 * This program is Copyright 2011, Jeffrey T. Darlington.
 * E-mail:  android_apps@gpf-comics.com
 * Web:     https://code.google.com/p/android-ppp/
 * 
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this
 * program.  If not, see http://www.gnu.org/licenses/.
*/
package com.gpfcomics.android.ppp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * The CardDBAdapter encapsulates all database actions for the Perfect Paper Passwords
 * application.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
class CardDBAdapter {

	/** I *think* this is used for the SQLiteOpenHelper.onUpgrade() log and
     *  nowhere else.  That said, I'm not sure what other purpose this
     *  constant may serve. */
    private static final String TAG = "PPP-CardDBAdapter";
    /** An instance of our internal DatabaseHelper class*/
    private DatabaseHelper mDbHelper;
    /** A reference to the underlying SQLiteDatabase */
    private SQLiteDatabase mDb;
    
    /** A constant representing the name of the database. */
    private static final String DATABASE_NAME = "ppp";
    /** A constant representing the card set data table in the database. */
    private static final String DATABASE_TABLE_CARDSETS = "cardsets";
    /** A constant representing the "strike outs" data table in the database. */
    private static final String DATABASE_TABLE_STRIKEOUTS = "strikeouts";
    /** The version of this database. */
    private static final int DATABASE_VERSION = 1;
    
    static final String KEY_CARDSETID = "_id";
    static final String KEY_NAME = "name";

    /** The SQL statement to create the card sets database table */
    private static final String DATABASE_CREATE_CARDSETS_SQL =
            "create table " + DATABASE_TABLE_CARDSETS +
            	" (" + KEY_CARDSETID + " integer primary key autoincrement, "
        		+ KEY_NAME + " text not null, sequence_key text not null, "
        		+ "alphabet text not null, columns integer not null, "
        		+ "rows integer not null, passcode_length integer not null, "
        		+ "last_card integer not null);";
    
    /** The SQL statement to create the "strike outs" database table */
    private static final String DATABASE_CREATE_STRIKEOUTS_SQL =
        	"create table " + DATABASE_TABLE_STRIKEOUTS +
        		" (" + KEY_CARDSETID + " integer primary key autoincrement, "
        		+ "cardset_id integer not null, "
        		+ "card integer not null, col integer not null, "
        		+ "row integer not null);";
    
    /** The SQL statement to create the primary index on the "strike out" table.
     *  This index creates a unique index on all four primary columns, which forces
     *  only one row per combination. */
    private static final String DATABASE_CREATE_INDEX1_SQL =
        	"create unique index strikeindxmain on " + DATABASE_TABLE_STRIKEOUTS +
        		" (cardset_id, card, col, row);";
    
    /** The SQL statement to create the card set index on the "strike out" table.
     *  This will be used identify all strike out data for a given card set. */
    private static final String DATABASE_CREATE_INDEX2_SQL =
    		"create index strikeindxcardset on " + DATABASE_TABLE_STRIKEOUTS +
    			" (cardset_id);";
    
    /** The SQL statement to create the card set and card number index on the "strike
     *  out table.  This will be used to find all strikes for a given card in a given
     *  card set. */
    private static final String DATABASE_CREATE_INDEX3_SQL =
        	"create index strikeindxcardsetcard on " + DATABASE_TABLE_STRIKEOUTS +
    			" (cardset_id, card);";


    /** Our calling Context. */
    private final PPPApplication theApp;

    /**
     * This helper wraps a little bit of extra functionality around the
     * default SQLiteOpenHelper, giving it a bit more code specific to
     * how Cryptnos works.
     * @author Jeffrey T. Darlington
	 * @version 1.0
	 * @since 1.0
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
    }
    
}
