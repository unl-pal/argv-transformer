package com.imiFirewall.activity;


import java.util.Date;
import java.util.HashMap;

import com.imiFirewall.R;
import com.imiFirewall.imiSql;
import com.imiFirewall.R.string;
import com.imiFirewall.R.xml;
import com.imiFirewall.util.imiUtil;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivitySetting extends PreferenceActivity implements OnPreferenceChangeListener,OnPreferenceClickListener{
	
	CheckBoxPreference warnCheckPref;
	EditTextPreference mobile_warn;
	EditTextPreference wifi_warn;
	Preference reset_data;
   
}