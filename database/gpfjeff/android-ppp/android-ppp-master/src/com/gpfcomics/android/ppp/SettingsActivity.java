/* SettingsActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 24, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This activity provides a UI for managing application-wide settings, such as
 * setting or clearing the master password and toggling the option to copy
 * passcodes to the clipboard.
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
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This activity provides an interface for accessing application-wide settings, such
 * as the user's password and whether or not to copy passcodes to the clipboard.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class SettingsActivity extends Activity {
	
	/** This constant identifies the first new password dialog */
	private static final int DIALOG_PASSWORD_SET1 = 920541;

	/** This constant identifies the second new password dialog */
	private static final int DIALOG_PASSWORD_SET2 = 920542;

	/** This constant identifies the clear password dialog */
	private static final int DIALOG_PASSWORD_CLEAR = 920543;

	/** This constant identifies the progress dialog */
	private static final int DIALOG_PROGRESS = 920544;
	
	/** This constant identifies the Help option menu */
	private static final int OPTMENU_HELP = Menu.FIRST;

	/** This constant signifies an error in the cryptography thread.  It states
	 *  that the password could not be set at the application level. */
	private static final int THREAD_ERROR_PASSWORD_SET_FAILED = -1;
	
	/** This constant signifies an error in the cryptography thread.  It states
	 *  that the password could not be cleared at the application level. */
	private static final int THREAD_ERROR_PASSWORD_CLEAR_FAILED = -2;
	
	/** This constant signifies an error in the cryptography thread.  It states
	 *  that there were no card sets in the database, so no cryptography is
	 *  necessary. */
	private static final int THREAD_ERROR_NO_CARD_SETS = -3;
	
	/** This constant signifies an error in the cryptography thread.  It states
	 *  that a fatal error occurred in the encryption or decryption process and
	 *  the sequence keys in the database may be in an unstable and unpredictable
	 *  state. */
	private static final int THREAD_ERROR_EXCEPTION = -1000;
	
	/** This constant will be used to tell the progress dialog that we are
	 *  setting rather than clearing the password. */
	private static final int ACTION_SET_PWD = 0;
	
	/** This constant will be used to tell the progress dialog that we are
	 *  clearing rather than setting the password. */
	private static final int ACTION_CLEAR_PWD = 1;
	
	/** The Set/Clear Password button */
	private Button btnSetPassword = null;
	
	/** The copy passcodes to the clipboard checkbox */
	private CheckBox chkCopyPasscodes = null;
	
	/** A handy reference to the ProgressDialog used when encrypting
	 *  and decrypting data in the database. */
	private ProgressDialog progressDialog = null;
	
	/** A reference back to the parent application */
	private PPPApplication theApp = null;
	
	/** This string holds the result of the first new password dialog box so it can
	 *  be compared with the result of the second dialog before the password is
	 *  actually set. */
	private String newPassword = null;
	
	/** This flag contains the current action state, i.e. whether or not we are
	 *  setting or clearing the password.  This will be used to communicate to
	 *  the progress dialog which action it should be performing, as well as
	 *  which messages to display. */
	private int currentAction = -1;
	
	/** A count of all card sets in the database */
	private int cardsetCount = 0;
	
	/** This object represents a worker thread that will perform the actual
	 *  cryptographic operations (encryption or decryption) on the sequence key
	 *  data in the database. */
	private SeqKeyCryptoThread seqKeyCryptoThread = null;
	
	/**
     * This Handler will be responsible for receiving and processing messages sent
     * from the encryption/decryption thread.
     */
    private final Handler handler = new Handler()
    {
    	public void handleMessage(Message msg) {
    		// Get the count of the number of sequence keys processed.  If this
    		// is a positive number, update the progress dialog accordingly.
    		int countDone = msg.getData().getInt("count");
    		if (countDone >= 0) progressDialog.setProgress(countDone);
    		// If it looks like we're finished (the count done equals the count of
    		// all card sets plus one), remove the progress dialog and inform
    		// the user of our success:
    		if (countDone >= cardsetCount + 1) {
    			removeDialog(DIALOG_PROGRESS);
    			// If we were encrypting, tell the user that the password was
    			// successfully set:
    			if (currentAction == ACTION_SET_PWD) {
    				Toast.makeText(getBaseContext(),
							R.string.dialog_password_set_success,
							Toast.LENGTH_LONG).show();
					// Change the button text on the main activity to state that
    				// pressing it again will clear the password:
					btnSetPassword.setText(R.string.settings_password_btn_clear);
					// For security reasons, clear the local cache of the
					// unencrypted password:
					newPassword = null;
				// If we were decrypting, tell the user the password was
				// successfully cleared:
    			} else {
    				Toast.makeText(getBaseContext(),
							R.string.dialog_password_clear_success,
							Toast.LENGTH_LONG).show();
					// Change the button text on the main activity to state that
    				// pressing it again will set a new password:
					btnSetPassword.setText(R.string.settings_password_btn_set);
    			}
    		// If the thread reports that there were no card sets to process, we
    		// can simply remove the progress dialog and do a simple set or clear
    		// with no regard to the encryption/decryption process:
    		} else if (countDone == THREAD_ERROR_NO_CARD_SETS) {
    			removeDialog(DIALOG_PROGRESS);
    			if (currentAction == ACTION_SET_PWD) {
    				if (theApp.setPassword(newPassword)) {
        				Toast.makeText(getBaseContext(),
    							R.string.dialog_password_set_success,
    							Toast.LENGTH_LONG).show();
    					btnSetPassword.setText(R.string.settings_password_btn_clear);
    				} else 
    					Toast.makeText(getBaseContext(),
    							R.string.dialog_password_set_failure,
    							Toast.LENGTH_LONG).show();
    			} else {
    				if (theApp.clearPassword()) {
        				Toast.makeText(getBaseContext(),
    							R.string.dialog_password_clear_success,
    							Toast.LENGTH_LONG).show();
    					btnSetPassword.setText(R.string.settings_password_btn_set);
    				} else 
    					Toast.makeText(getBaseContext(),
    							R.string.dialog_password_clear_failure,
    							Toast.LENGTH_LONG).show();
    			}
    		// If the thread reports that the password could not be set, notify
    		// the user:
    		} else if (countDone == THREAD_ERROR_PASSWORD_SET_FAILED) {
				Toast.makeText(getBaseContext(),
						R.string.dialog_password_set_failure,
						Toast.LENGTH_LONG).show();
			// If the thread reports that the password could not be cleared, notify
			// the user:
    		} else if (countDone == THREAD_ERROR_PASSWORD_CLEAR_FAILED) {
				Toast.makeText(getBaseContext(),
						R.string.dialog_password_clear_failure,
						Toast.LENGTH_LONG).show();
    		// If something blew up, we'll get this generic exception error message.
			// Tell the user that the database could be corrupted (some sequence keys
			// might be encrypted while some may not).
    		} else if (countDone == THREAD_ERROR_EXCEPTION) {
    			if (currentAction == ACTION_SET_PWD) {
    				Toast.makeText(getBaseContext(),
						R.string.dialog_progress_encrypting_error,
						Toast.LENGTH_LONG).show();
    			} else {
    				Toast.makeText(getBaseContext(),
    						R.string.dialog_progress_decrypting_error,
    						Toast.LENGTH_LONG).show();
    			}
    		}
    	}
    };
    
    /**
     * This subclass of Thread will be used to do the dirty work of encrypting and
     * decrypting the sequence keys in the database.  Since this is a process that
     * may potentially be lengthy, we should do this outside the UI thread.
     * Messages should be passed back to the Handler specified in the constructor. 
     * @author Jeffrey T. Darlington
     * @version 1.0
     * @since 1.0
     */
    private class SeqKeyCryptoThread extends Thread
    {
    	/** The Handler to update our status to */
		private Handler handler;
		/** A reference to the overall application */
		private PPPApplication theApp = null;
    }

}

