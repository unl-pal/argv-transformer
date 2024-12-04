package com.bashizip.andromed;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bashizip.andromed.R;
import com.bashizip.andromed.application.AndromedApplication;
import com.bashizip.andromed.data.Category;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.data.Post;
import com.bashizip.andromed.data.Quote;
import com.bashizip.andromed.data.Status;
import com.bashizip.andromed.datahelper.DBTool;
import com.db4o.config.TVector;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPost extends Activity implements OnClickListener {

	// private DataBaseConnection dbcon;
	SharedPreferences preferences;

	EditText tf_nom, tv_age,
	         tv_illness_days;

	CheckBox cb_underTreatement;

	Spinner spin_cat;
	
	Button btn_save, btn_annuler;
	

	// AndromedApplication app;

	private Post post;
	private DBTool dbcon;

}
