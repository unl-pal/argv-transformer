package com.bashizip.andromed;

import java.util.Calendar;

import com.bashizip.andromed.data.Quote;
import com.bashizip.andromed.datahelper.DBTool;


import android.app.Activity;
import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewQuote extends Activity implements OnClickListener{

	private DBTool dbcon;
	private EditText editor;
	private Button btn_save_quote,btn_ann_quote;
	
	SharedPreferences preferences;
		
		
	   
	   
}
