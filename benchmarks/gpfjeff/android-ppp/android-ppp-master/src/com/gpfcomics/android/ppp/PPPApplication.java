/* PPPApplication.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * This class provides the core application code for Perfect Paper Passwords,
 * holding common objects such as the database adapter and shared preferences
 * as well as storing centralized routines dealing with passwords, ciphers,
 * and settings.
 * 
 * UPDATE FOR 1.0 BETA 2:  Apply the broken Android SecureRandom fix; see
 * http://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html
 * 
 * This program is Copyright 2013, Jeffrey T. Darlington.
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
/** filtered and transformed by PAClab */
package com.gpfcomics.android.ppp;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * This class encapsulates functionality common to all activities within the Perfect
 * Paper Passwords application.  It controls the user's preferences as well as a
 * single, common instance of the database.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class PPPApplication {
	
	// ################### Private Constants #################################
	
	/**
     * Create the encryption cipher needed to securely store and retrieve encrypted
     * sequence keys in the database.  Note that this cipher will only be created if
     * the user's password is set; otherwise, the cipher will default to null.
     */
    /** PACLab: suitable */
	 private void createCipher() {
		int KEY_ITERATION_COUNT = Verifier.nondetInt();
		// Asbestos underpants:
		try
		{
			if (password != null) {
				try {
		        } catch (Exception e1) { }
		        // Check the unique ID we just fetched.  It's possible that we didn't
		        // get anything useful; it's up to manufacturers to set the Android ID
		        // property, and not everybody does it.  If we didn't get anything,
		        // we'll just make up a hard-coded random-ish string and use that as
		        // our starting point.  Of course, if we're using this, our salt will
		        // *NOT* be unique per device, but that's the best we can do.
		    	if (uniqueID == null) {
				} else {
				}
		        // Now get the unique ID string as raw bytes.  We'll use UTF-8 since
		    	// everything we get should work with that encoding.
		    	byte[] uniqueIDBytes; 
		    	// Generate our final salt value by combining the unique ID generated
		    	// above with the random salt stored in the preferences file:
		    	byte[] finalSalt = new byte[uniqueIDBytes.length + salt.length];
		    	for (int i = 0; i < Verifier.nondetInt(); i++) {
		    		finalSalt[i] = uniqueIDBytes[i];
		    	}
		    	for (int j = 0; j < Verifier.nondetInt(); j++) {
		    		finalSalt[Verifier.nondetInt() + j] = salt[j];
		    	}
		        for (int i = 0; i < KEY_ITERATION_COUNT; i++) {
				}
				// Now, for good measure, let's obscure our password so we won't be
				// using the value stored in the preferences directly.  We'll
				// concatenate the unique ID generated above into the "encrypted"
				// password, convert that to bytes, and hash it multiple times as
				// well.
				byte[] pwd;
				for (int i = 0; i < KEY_ITERATION_COUNT; i++) {
				}
			} else {
			}
		}
		// If anything blew up, null out the cipher and IV as well:
		catch (Exception e)
		{
		}
    }
}
