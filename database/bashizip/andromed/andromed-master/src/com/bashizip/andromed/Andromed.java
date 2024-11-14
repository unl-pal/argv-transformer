package com.bashizip.andromed;



import java.util.Vector;

import com.bashizip.andromed.R;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class Andromed extends Activity implements OnClickListener {
	
	private static int CODE_RETOUR = 1;
	SharedPreferences preferences; 
	
	Button btnInvestigation, quoteList, btn_sync, btn_stored_cases,
			btn_settings;

	private Dialog aboutDialog;
	

	public static final int MENU_ITEM_WEB = Menu.FIRST;
	public static final int MENU_ITEM_ABOUT = Menu.FIRST + 1;

}