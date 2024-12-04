/* NewCardActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This activity provides the main UI for creating a new card set.  When launched,
 * it populates all the parameters with sane defaults, such as a generic card set
 * name, a randomly generated sequence key, and the default alphabet and number of
 * rows, columns, passcode length, etc.  It provides error checking on the inputs
 * (mostly calling the validation methods in the Cardset class) and stores the card
 * set data to the database if the user decides to keep the new card set.
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewCardActivity extends Activity {
	
	/** This constant identifies the Help option menu */
	private static final int OPTMENU_HELP = Menu.FIRST;

	/** The passcode spinner contains a list of valid numbers for the length of the
	 *  passcode.  By the definitions at GRC.com, this should be 2-16 characters.
	 *  The exact values are specified within the res/values/strings.xml file, but
	 *  these will just be strings of numbers.  Fortunately, we can do a bit of simple
	 *  math to get the actual value of the spinner from the position index.  Since
	 *  the first item will be 2, which is in position 0, we can add 2 to the position
	 *  to get the actual value.  Conversely, you can get the position from the
	 *  passcode length by subtracting 2.  This constant lets us define this "offset"
	 *  in a single place should it ever need to change. */
	private static final int PASSCODE_SPINNER_OFFSET = 2;
	
	/** The card set name text box */
	private EditText txtName = null;
	
	/** The number of columns text box */
	private EditText txtNumColumns = null;
	
	/** The number of rows text box */
	private EditText txtNumRows = null;
	
	/** The passcode length Spinner */
	private Spinner spinPasscodeLength = null;
	
	/** The alphabet text box */
	private EditText txtAlphabet = null;
	
	/** The sequence key text box */
	private EditText txtSequenceKey = null;
	
	/** The Add New Card Set button */
	private Button btnAdd = null;
	
	/** A reference back to our parent application */
	private PPPApplication theApp = null;
	
	/** A reference to our card database helper */
	private CardDBAdapter DBHelper = null;
	
	/** A Cardset object, which will store the current state of the card set */
	private Cardset cardSet = null;
    
}
