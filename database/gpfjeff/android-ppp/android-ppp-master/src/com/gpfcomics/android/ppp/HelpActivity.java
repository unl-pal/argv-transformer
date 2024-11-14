/* HelpActivity.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          December 10, 2009
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * REQUIRES:      
 * REQUIRED BY:   
 * 
 * This activity displays an individual help item.  It is launched from the
 * Help item in the other activity menus, which will pass it a resource ID of
 * the help text via an Intent.  This activity is actually pretty dumb; aside
 * from a static TextView that basically says "Tap Back to return to the help
 * menu", it just displays whatever text it's been told to display.
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
import android.os.Bundle;
import android.widget.TextView;

/**
 * This activity displays an individual help item.  It is launched from the
 * Help item in the other activity menus, which will pass it a resource ID of
 * the help text via an Intent.  This activity is actually pretty dumb; aside
 * from a static TextView that basically says "Tap Back to return to the help
 * menu", it just displays whatever text it's been told to display.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class HelpActivity extends Activity {

	/** The TextView that will contain the main body of our help text. */
	TextView helpText = null;
    
}
