package com.jy.multimedia;

import com.jy.multi_media.R;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HistoryActivity extends Activity {

	private String db_path = MainActivity.db_path;
	private ListView listView;
	private SQLiteDatabase db;

}