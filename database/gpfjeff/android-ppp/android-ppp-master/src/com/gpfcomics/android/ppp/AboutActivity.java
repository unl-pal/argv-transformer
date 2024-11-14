/* AboutActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 24, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This activity serves as the "about" page for the application, providing copyright,
 * authorship, acknowledgments, and licensing information.  The full GPLv3 text is
 * accessible by launching the browser (via intent) and sending the user to the
 * GNU.org site.
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
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Display a brief bit of information about the application as a whole
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class AboutActivity extends Activity {
	
	/** This constant identifies the View Full License option menu */
	private static final int OPTMENU_LICENSE = Menu.FIRST;
	
	/** The URL to the GNU GPLv3 */
	private static final String GPL3_URL =
		"http://www.gnu.org/licenses/gpl-3.0-standalone.html";
	
	/** The TextView that contains the version number string */
	private TextView lblVersion = null;


}
