package com.example.lab6.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.example.lab6.R;
import com.example.lab6.sounddownloader.SoundDownloaderActivity;
import com.example.lab6.utils.Utils;

/**
 * UserSettingsActivity.java
 * 
 * Uses preferences.xml for user settings. Settings for choice of voice files,
 * download and storage location of sound files, and deletion of sound files.
 */
public class UserSettingsActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
}