package com.example.lab6.dialer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lab6.R;
import com.example.lab6.utils.Utils;

/**
 * DialerView.java
 * 
 * Custom component consisting of DialPadView and NumPadView. Handles the logic
 * of both components. When a numpad is clicked or pressed using a keyboard, a
 * corresponding sound will play and the number/symbol will be appended to the
 * dialpad. The arrowpad erases a number or clears the whole number when
 * performing a long click. The callpad calls the number entered in the dialpad.
 */
public class DialerView extends LinearLayout implements
		OnSharedPreferenceChangeListener {
	private SoundPool soundPool;
	private int soundID1, soundID2, soundID3, soundID4, soundID5, soundID6,
			soundID7, soundID8, soundID9, soundIDStar, soundID0, soundIDPound;
	private boolean soundFilesLoaded;

	private EditText editText;
	private SparseArray<ImageButton> imageButtons;

	// OnClickListener for all pads. Plays a corresponding sound file.
	// Swapping images is done in XML. The arrow pad erases one letter
	// and the call pad calls the number entered in the dialpad.
	final OnClickListener onClickListener = new OnClickListener() {
		public void onClick(final View v) {
			switch (v.getId()) {
			case R.id.imageButton_dialpad_1:
				playSound(soundID1);
				appendText("1");
				break;
			case R.id.imageButton_dialpad_2:
				playSound(soundID2);
				appendText("2");
				break;
			case R.id.imageButton_dialpad_3:
				playSound(soundID3);
				appendText("3");
				break;
			case R.id.imageButton_dialpad_4:
				playSound(soundID4);
				appendText("4");
				break;
			case R.id.imageButton_dialpad_5:
				playSound(soundID5);
				appendText("5");
				break;
			case R.id.imageButton_dialpad_6:
				playSound(soundID6);
				appendText("6");
				break;
			case R.id.imageButton_dialpad_7:
				playSound(soundID7);
				appendText("7");
				break;
			case R.id.imageButton_dialpad_8:
				playSound(soundID8);
				appendText("8");
				break;
			case R.id.imageButton_dialpad_9:
				playSound(soundID9);
				appendText("9");
				break;
			case R.id.imageButton_dialpad_star:
				playSound(soundIDStar);
				appendText("*");
				break;
			case R.id.imageButton_dialpad_0:
				playSound(soundID0);
				appendText("0");
				break;
			case R.id.imageButton_dialpad_pound:
				playSound(soundIDPound);
				appendText("#");
				break;
			case R.id.imageButton_dialpad_arrow:
				eraseLetter();
				break;
			case R.id.imageButton_dialpad_call:
				call();
				break;
			}
		}
	};

	// OnLongClickListener for the arrow pad. Clears the number.
	final OnLongClickListener onLongClickListener = new OnLongClickListener() {
		public boolean onLongClick(final View v) {
			switch (v.getId()) {
			case R.id.imageButton_dialpad_arrow:
				clearText();
				break;
			}
			return true;
		}
	};
}