package com.jy.music;

import com.jy.multi_media.R;
import com.jy.multimedia.MainActivity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MusicPlayer extends Activity {

	// ���岥��������MediaPlayer
	private MediaPlayer mediaPlayer;
	private List<String> MusicList = MusicActivity.MusicList;
	private String db_path = MainActivity.db_path;
	private int position;
	private Intent intent;
	private SQLiteDatabase db;
	private int i = 0;
	private Button control;

}