package edu.css.cis3334.vogellasampleinclass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "comments";     // name of the table
    public static final String COLUMN_ID = "_id";               // name of the id field
    public static final String COLUMN_COMMENT = "comment";      // name of the comment field

    private static final String DATABASE_NAME = "commments.db"; // name of the database
    private static final int DATABASE_VERSION = 1;              // version of the database

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "(" +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_COMMENT + " text not null, ";

}