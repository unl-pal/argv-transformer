package com.jy.video;

import com.jy.multi_media.R;
import com.jy.multimedia.MainActivity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends Activity {

	private List<String> VideoList = VideoActivity.VideoList;
	private String db_path = MainActivity.db_path;
	private int position;
	private Intent intent;
	private SQLiteDatabase db;
	private int i = 0;

}