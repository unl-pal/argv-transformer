/* MainMenuActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * The main menu activity for Perfect Paper Passwords.  This activity lists the
 * currently available card sets, allowing the user to add a new one, select one
 * to display cards for, or otherwise manipulate the card sets as entire sets.
 * Card sets may be renamed, deleted, or have all their "strike-through" data
 * cleared.  Aside from these features, further editing of card set data is not
 * allowed.
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
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The main menu activity for Perfect Paper Passwords.  This activity lists the
 * currently available card sets, allowing the user to add a new one, select one
 * to display cards for, or otherwise manipulate the card sets as entire sets.
 * Card sets may be renamed, deleted, or have all their "strike-through" data
 * cleared.  Aside from these features, further editing of card set data is not
 * allowed.
 * @author Jeffreyt T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class MainMenuActivity extends ListActivity {

	/** This constant identifies the Details context menu */
	private static final int MENU_DETAILS = Menu.FIRST;

	/** This constant identifies the Rename context menu */
	private static final int MENU_RENAME = Menu.FIRST + 1;

	/** This constant identifies the Clear All Strikes context menu */
	private static final int MENU_CLEAR_STRIKES = Menu.FIRST + 2;
	
	/** This constant identifies the Delete context menu */
	private static final int MENU_DELETE = Menu.FIRST + 3;

	/** This constant identifies the New option menu */
	private static final int OPTMENU_NEW = Menu.FIRST + 100;
	
	/** This constant identifies the Delete All option menu */
	private static final int OPTMENU_DELETE_ALL = Menu.FIRST + 101;
	
	/** This constant identifies the Settings option menu */
	private static final int OPTMENU_SETTINGS = Menu.FIRST + 102;

	/** This constant identifies the Help option menu */
	private static final int OPTMENU_HELP = Menu.FIRST + 103;

	/** This constant identifies the About option menu */
	private static final int OPTMENU_ABOUT = Menu.FIRST + 104;

	/** This constant identifies the Confirm Delete dialog */
	private static final int DIALOG_CONFIRM_DELETE = 1000;

	/** This constant identifies the Confirm Delete All dialog */
	private static final int DIALOG_CONFIRM_DELETE_ALL = 1001;

	/** This constant identifies the Rename dialog */
	private static final int DIALOG_RENAME = 1002;

	/** This constant identifies the Confirm Clear All Strikes dialog */
	private static final int DIALOG_CONFIRM_CLEAR_STRIKES = 1003;
	
	/** This constant identifies a request to another activity launched via
	 *  startActivityForResult() that we want to create a new card set */
	static final int REQUEST_NEW_CARDSET = 5000;
	
	/** This constant identifies a request to another activity launched via
	 *  startActivityForResult() that we want to view the last card of the
	 *  selected card set. */
	static final int REQUEST_CARD_VIEW = 5001;
	
	/** This constant identifies the return result from a called activity
	 *  has ended with an error and the operation could not be completed
	 *  successfully. */
	static final int RESPONSE_ERROR = 6000;
	
	/** This constant identifies the return result from a called activity
	 *  has been successful and the requested action is complete. */
	static final int RESPONSE_SUCCESS = 6001;

	/** The New, Add, or Create button */
	private Button btnNew = null;
	
	/** A reference back to our parent application */
	private PPPApplication theApp = null;
	
	/** A reference to the database helper */
	private CardDBAdapter DBHelper = null;
	
	/** A Cursor to feed the ListView */
	private Cursor cardsetCursor = null;

	/** The internal database ID of the currently selected card set.  This is
	 *  used in dialog boxes and such and is set by the context menu selection. */
	private long selectedCardsetID = -1l;
	
	/** A string holding the name of the currently selected card set.  This is
	 *  used in dialog boxes and such and is set by the context menu selection. */
	private String selectedCardsetName = null;
}