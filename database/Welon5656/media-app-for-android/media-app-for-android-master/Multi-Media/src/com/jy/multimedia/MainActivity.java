package com.jy.multimedia;

import com.jy.image.ImageActivity;
import com.jy.multi_media.R;
import com.jy.music.MusicActivity;
import com.jy.video.VideoActivity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private Intent intent_image, intent_video, intent_music;
	private ImageButton btn_image, btn_video, btn_music;
	public static String db_path;

}
