package com.example.lab5_1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * MainActivity.java
 * 
 * Displays a menu where the user can navigate to TranslateActivity, show an
 * about dialog, or quit the application.
 * 
 * Plays music and resumes the track position when returning to the activity. A
 * user setting for turning off the music is available in the phone's menu or
 * the overflow menu.
 */
public class MainActivity extends Activity {
	static final String STATE_TRACK_POS = "com.example.Lab5_1.CURRENT_POS";
	static final String TRANSLATION_SETTING = "com.example.Lab5_1.TRANSLATION_SETTING";

	private MediaPlayer mediaPlayer;
	private boolean playMusic;

}
