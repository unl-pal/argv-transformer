package com.example.lab5_1;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * TranslateActivity.java
 * 
 * Translates input from a text field to or from Robber Language depending on
 * the translation setting. Option to clear the text on the context menu, the
 * phone menu or as an action item. Saves both of the translated texts to
 * SharedPreferences.
 */
public class TranslateActivity extends Activity {
	static final String TRANSLATION_OUTPUT = "translationOutput";

	private int translation;
	private SharedPreferences settings;
	private EditText editTextOutput;
}
