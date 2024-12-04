/* CardViewActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          July 19, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * [Description]
 * 
 * This program is Copyright 2011, Jeffrey T. Darlington.
 * E-mail:  android_apps@gpf-comics.com
 * Web:     https://code.google.com/p/android-ppp/
 * 
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this
 * program.  If not, see http://www.gnu.org/licenses/.
*/
package com.gpfcomics.android.ppp;

import java.util.ArrayList;

import com.gpfcomics.android.ppp.jppp.PPPengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SinglePasscodeActivity extends Activity {

	/** This constant identifies the Help option menu */
	private static final int OPTMENU_HELP = Menu.FIRST;

	/** A convenience constant pointing to the "on" or "struck through" Drawable
	 *  resource for our card's ToggleButtons. */
	private static final int toggleBgOn = R.drawable.strikethru_on;
	
	/** A convenience constant pointing to the "off" or "cleared" Drawable
	 *  resource for our card's ToggleButtons. */
	private static final int toggleBgOff = R.drawable.strikethru_off;
	
	/** This array lists the letters of the alphabet to be used for our column
	 *  headings.  These may get moved to the res/values/strings.xml resource
	 *  eventually. */
	private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H",
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T" /*, "U", "V", "W",
		"X", "Y", "Z"*/};
	
	/** A Cardset object representing the card set we are currently using. */
	private Cardset cardSet = null;

	/** The jPPP Perfect Paper Passwords engine which does the actual work of
	 *  generating passcodes */
	private PPPengine ppp = null;
	
	/** A reference back to our parent application */
	private PPPApplication theApp = null;
	
	/** A reference to our database helper */
	private CardDBAdapter DBHelper = null;
	
	/** The card set title label */
	private TextView lblCardTitle = null;

	/** The card number text box */
	private EditText txtCardNumber = null;
	
	/** The column number Spinner */
	private Spinner spinColumn = null;

	/** The row number Spinner */
	private Spinner spinRow = null;
	
	/** The passcode ToggleButton */
	private ToggleButton tbPasscode = null;

}
