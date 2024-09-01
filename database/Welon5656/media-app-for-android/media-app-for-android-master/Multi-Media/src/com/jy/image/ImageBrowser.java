package com.jy.image;

import com.jy.multi_media.R;
import com.jy.multimedia.MainActivity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ImageView;

public class ImageBrowser extends Activity implements OnGestureListener {
	
	private List<String> ImageList = ImageActivity.ImageList;
	private String db_path = MainActivity.db_path;
	private int position;
	private Intent intent;
	private ImageView img;
	private SQLiteDatabase db;
	final int FLIP_DISTANCE = 50;
	GestureDetector detector;
	
}