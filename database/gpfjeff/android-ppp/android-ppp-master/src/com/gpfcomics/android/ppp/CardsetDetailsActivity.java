/* CardsetDetailsActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          March 7, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This activity displays a brief summary of the parameters that define a given
 * card set, as well as a few interesting statistics, like the total number of
 * strike-outs in the set.  While this data is displayed in a read-only format,
 * the option menu includes several of the same actions that can be found in the
 * context menu of the main menu list:  rename, clear toggles, delete, etc.  There
 * is also an option menu item to go to the card view activity and see the current
 * card.
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity displays a brief summary of the parameters that define a given
 * card set, as well as a few interesting statistics, like the total number of
 * strike-outs in the set.  While this data is displayed in a read-only format,
 * the option menu includes several of the same actions that can be found in the
 * context menu of the main menu list:  rename, clear toggles, delete, etc.  There
 * is also an option menu item to go to the card view activity and see the current
 * card.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class CardsetDetailsActivity extends Activity {
	
	/** This constant identifies the View option menu */
	private static final int OPTMENU_VIEW = Menu.FIRST;

	/** This constant identifies the Rename option menu */
	private static final int OPTMENU_RENAME = Menu.FIRST + 1;

	/** This constant identifies the Clear All Strikes option menu */
	private static final int OPTMENU_CLEAR_STRIKES = Menu.FIRST + 2;
	
	/** This constant identifies the Delete option menu */
	private static final int OPTMENU_DELETE = Menu.FIRST + 3;

	/** This constant identifies the Help option menu */
	private static final int OPTMENU_HELP = Menu.FIRST + 4;
	
	/** This constant identifies the Confirm Delete dialog */
	private static final int DIALOG_CONFIRM_DELETE = 1000;

	/** This constant identifies the Rename dialog */
	private static final int DIALOG_RENAME = 1001;

	/** This constant identifies the Confirm Clear All Strikes dialog */
	private static final int DIALOG_CONFIRM_CLEAR_STRIKES = 1002;
	
	/** The card set name label */
	private TextView labelCardsetName = null;
	/** The last card label */
	private TextView labelLastCardValue = null;
	/** The strike-out or toggle count label */
	private TextView labelCountTogglesValue = null;
	/** The number of columns label */
	private TextView labelNumColumnsValue = null;
	/** The number of rows label */
	private TextView labelNumRowsValue = null;
	/** The passcode length label */
	private TextView labelPasscodeLengthValue = null;
	/** The alphabet label */
	private TextView labelAlphabetValue = null;
	/** The sequence key label */
	private TextView labelSequenceKeyValue = null;

	/** A reference back to our parent application */
	private PPPApplication theApp = null;
	
	/** A reference to our database helper */
	private CardDBAdapter DBHelper = null;
	
	/** The internal database ID for the selected card set */
	private Cardset cardSet = null;
    
}
