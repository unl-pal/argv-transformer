/* CardViewActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This Card View Activity does the actual work of displaying a Perfect Paper Passwords
 * card.  Given a Cardset definition, it displays the "last" or "current" card for
 * that card set, as well as providing an interface for moving from card to card and
 * "striking out" used passcodes.
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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gpfcomics.android.ppp.jppp.PPPengine;;

/**
 * This Card View Activity does the actual work of displaying a Perfect Paper Passwords
 * card.  Given a Cardset definition, it displays the "last" or "current" card for
 * that card set, as well as providing an interface for moving from card to card and
 * "striking out" used passcodes.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class CardViewActivity extends Activity implements
	GestureOverlayView.OnGesturePerformedListener {
	
	/** A constant indicating that we should show the progress dialog during
	 *  the card rebuilding process. */
	private static final int DIALOG_PROGRESS = 1234567;
	
	/** A constant indicating that we should show the Go To Card dialog */
	private static final int DIALOG_GOTO = 1234568;
	
	/** A constant indicating that we should show the Clear Toggles dialog */
	private static final int DIALOG_CLEAR_TOGGLES = 1234569;
	
	/** A constant identifying the Go To option menu item */
	private static final int OPTMENU_GOTO = 54320;
	
	/** A constant identifying the View Details option menu item */
	private static final int OPTMENU_DETAILS = 54321;
	
	/** A constant identifying the Clear Toggles option menu item */
	private static final int OPTMENU_CLEAR_TOGGLES = 54322;
	
	/** A constant identifying the Settings option menu item */
	private static final int OPTMENU_SETTINGS = 54323;
	
	/** A constant identifying the Help option menu item */
	private static final int OPTMENU_HELP = 54324;
	
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
	
	/** A "seed" value which will be applied to the internal IDs of the card's
	 *  ToggleButtons.  This will help us identify which button was pressed.  All
	 *  ToggleButtons on the card must have this value added into the final ID
	 *  number. */
	private static final int btnIdSeed = 1000000;
	
	/** An "offset" value which will be applied to a ToggleButton's row coordinate
	 *  before the row and column values are factored into the button's internal
	 *  ID. */
	private static final int btnRowOffset = 1000;
	
	/** The Previous Card button */
	private Button btnPrevious = null;
	
	/** The Next Card button */
	private Button btnNext = null;
	
	/** The card set title label */
	private TextView lblCardTitle = null;
	
	/** The card number label */
	private TextView lblCardNumber = null;
	
	/** The gesture overlay, so we can process swipe gestures */
	private GestureOverlayView gestureOverlay = null;
	
	/** The gesture library containing our recognized gestures */
	private GestureLibrary gestureLibrary = null;
	
	/** A handy reference to the ProgressDialog used when rebuilding cards */
	private ProgressDialog progressDialog = null;

	/** A Cardset object representing the card set we are currently using. */
	private Cardset cardSet = null;

	/** The jPPP Perfect Paper Passwords engine which does the actual work of
	 *  generating passcodes */
	private PPPengine ppp = null;
	
	/** A reference back to our parent application */
	private PPPApplication theApp = null;
	
	/** A reference to our database helper */
	private CardDBAdapter DBHelper = null;
	
	/** This worker thread allows us to move the computationally expensive passcode
	 *  generation step into a different thread from the UI.  This is required for
	 *  a good Android user experience. */
	private CardBuilderThread cardBuilderThread = null;
	
	/** The total number of passcodes on the card.  This should be the product of
	 *  the number of rows and the number of columns on the card.  This is pulled into
	 *  a variable so we only have to compute the value once. */
	private int totalPasscodes = 0;
	
	/** This two-dimensional Boolean array stores the toggled or "strike through"
	 *  state of the card's various ToggleButtons.  The first dimension is for rows
	 *  and the second for columns.  If a passcode has been "struck", the value for
	 *  its position will be true; otherwise it will be false.  Note that array
	 *  indices are zero-based while the row/column numbers are one-based. */
	private boolean[][] toggles = null;
	
	/** This two-dimensional String array stores the generated passcode values for the
	 *  card's various ToggleButtons.  The first dimension is for rows and the second
	 *  for columns.  Note that array indices are zero-based while the row/column
	 *  numbers are one-based. */
	private String[][] passcodes = null;

	/** This Handler receives status updates from the CardBuilderThread and updates
     *  the progress dialog accordingly. */
    final Handler handler = new Handler() {
    	public void handleMessage(Message msg) {
    		// Get the status code from the message:
    		int status = msg.getData().getInt("pccount");
    		// If we got any positive status, it's not an error, so update the
    		// progress dialog with the value:
    		if (status >= 0) progressDialog.setProgress(status);
    		// If we've reached the end of our passcodes, it's time to start updating
    		// the actual ToggleButtons.  We can't do this directly from the worker
    		// thread, so it has to wait until we get to here.
    		if (status >= totalPasscodes) {
    			try {
	    			// If we got any useful data, it's time to update the buttons:
	    			if (passcodes != null) {
	    				// Loop through the rows and columns:
	    				for (int row = 1; row <= cardSet.getNumberOfRows(); row++) {
	    					for (int col = 1; col <= cardSet.getNumberOfColumns(); col++) {
	    		        		// Identify the ToggleButton for this row/column.  We'll
	    						// take the row and column number values to rebuild the
	    						// button's ID.
	    		        		ToggleButton tb = (ToggleButton)findViewById(btnIdSeed +
	    		        				row * btnRowOffset + col);
	    		        		// The button text should always display the passcode value,
	    		        		// regardless of state:
	    		    			tb.setTextOn(passcodes[row - 1][col - 1]);
	    		    			tb.setTextOff(passcodes[row - 1][col - 1]);
	    		    			// If we got useful toggle data and this passcode has been
	    		    			// toggled, "strike through" this button so the passcode
	    		    			// remains struck.  Note that the toggle array indices are
	    		    			// zero-based, so we need to adjust our values to get the right
	    		    			// state.  If we didn't get any useful toggle data, default
	    		    			// all toggles to false or off.
	    		    			if (toggles != null && toggles[row - 1][col - 1])
	    		    				tb.setChecked(true);
	    		    			else tb.setChecked(false);
	    					}
	    				}
	    				// Originally, I cleared the passcode and toggle arrays here to
	    				// free up memory.  Rather than do that now, we hold on to them
	    				// so we can restore the state of the view if there's a
	    				// configuration change.
	    			}
	    			// Now remove the progress dialog:
	    			removeDialog(DIALOG_PROGRESS);
    			} catch (Exception e) {
    				Toast.makeText(getBaseContext(), "ERROR: " + e.toString(),
                    		Toast.LENGTH_LONG).show();
    			}
    		// If we get a negative status, something went wrong.  Display an
    		// error message:
    		} else if (status < 0) {
    			Toast.makeText(getBaseContext(), R.string.error_card_build_failed,
                		Toast.LENGTH_LONG).show();
    		}
    	}
    };
    
    /**
     * This private Thread subclass does the grunt work of generating a card full of
     * passcodes.  Since this is very processor intensive, we need to do this in a
     * different thread than the UI thread.
     * @author Jeffrey T. Darlington
     * @version 1.0
     * @since 1.0
     */
    private class CardBuilderThread extends Thread {
    	/** The Handler to send messags to */
    	Handler handler = null;
    	/** The current count of passcodes generated */
        int counter = 0;
        
    }

}
