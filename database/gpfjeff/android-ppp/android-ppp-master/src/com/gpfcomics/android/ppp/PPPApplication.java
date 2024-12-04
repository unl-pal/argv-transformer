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
package com.gpfcomics.android.ppp;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * This class encapsulates functionality common to all activities within the Perfect
 * Paper Passwords application.  It controls the user's preferences as well as a
 * single, common instance of the database.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class PPPApplication extends Application {
	
	// ################### Private Constants #################################
	
	/** This constant is used to specify the name of our preferences file. */
	private static final String PREF_FILE = "PPPPrefs";

	/** This constant is used in the preferences file to identify the version of
	    Perfect Paper Passwords that is last wrote to the file. */
	private static final String PREF_VERSION = "version";
	
	/** This constant is used in the preferences file to identify the user's
	    password, if set.  If this option is not found in the preferences, there
		is no current password. */
	private static final String PREF_PASSWORD = "password";
	
	/** This constant is used in the preferences file to identify the user's
	    preference with regard to whether or not passcodes should be copied to
		the clipboard when they are "struck through". */
	private static final String PREF_COPY_PASSCODES_TO_CLIPBOARD = "pass_to_clip";
	
	/** This constant is used in the preferences file to identify the salt used
	 *  for cryptogrphaic operations. */
	private static final String PREF_SALT = "salt";
	
	/** The number of iterations used for cryptographic key generation, such
	 *  as in creating an AlgorithmParameterSpec.  Ideally, this should be
	 *  fairly high, but we'll use a modest value for performance. */
	private static final int KEY_ITERATION_COUNT = 50;

	/** The cryptographic hash to use to generate encryption salts.  Pass this
	 *  into MessageDigest.getInstance() to get the MessageDigest for salt
	 *  generation. */
	private static final String SALT_HASH = "SHA-512";

	/** A random-ish string for salting our encryption salts. */
	private static final String SALT = "cSg6Vo1mV3hsENK6njMIkr8adrZ4lbGByu8fd8PClRknqhEC8DOmbDCtgUAtbir";
	
	/** The character encoding used to convert strings to binary data, primarily
	 *  in cryptographic hash operations. */
	private static final String ENCODING = "UTF-8";
	
	/** The size of the AES encryption key in bits */
	private static final int KEY_SIZE = 256;

	/** The size of the AES encryption initialization vector (IV) in bits */
	private static final int IV_SIZE = 128;
	
	// ################### Private Members #################################
	
	/** A reference to the application's database helper.  Activities will get
	    copies of this reference, but the application will own the master copy. */
	private static CardDBAdapter DBHelper = null;
	
	/** A referenceto the application's shared preferences.  Activities will get
	    copies of this reference, but the application will own the master copy. */
	private static SharedPreferences prefs = null;
	
	/** Whether or not to copy passcodes to the clipboard when they are "struck
	    through" in the card view. */
	private static boolean copyPasscodes = true;
	
	/** A convenience reference to our numeric version code */
	private static int versionCode = -1;
	
	/** A convenience reference to our version "number" string */
	private static String versionName = null;
	
	/** An cipher for the encryption and decryption of sequence keys */
	private static BufferedBlockCipher cipher = null;
	
	/** The initialization vector (IV) used by our cipher */
	private static ParametersWithIV iv = null;
	
	private byte[] salt = null;
	
	// ################### Public Methods #################################
	
	/**
     * Create the encryption cipher needed to securely store and retrieve encrypted
     * sequence keys in the database.  Note that this cipher will only be created if
     * the user's password is set; otherwise, the cipher will default to null.
     */
    private void createCipher() {
		// Asbestos underpants:
		try
		{
			// The first thing we need to do is check to see if we have a password
			// set.  There's no point doing anything if there's no password.
			String password = prefs.getString(PREF_PASSWORD, null);
			if (password != null) {
				// OK, we've got a password.  Let's start by generating our salt.
		        // To try and make this unique per device, we'll use the device's
				// unique ID string.  To avoid the whole deprecation issue surrounding
		        // Settings.System.ANDROID_ID vs. Settings.Secure.ANDROID_ID, we'll
		        // wrap the call to this property inside the AndroidID class.  See
		        // that class for more details.
		        String uniqueID = null;
		        try {
		        	AndroidID id = AndroidID.newInstance(this);
		        	uniqueID = id.getAndroidID();
		        } catch (Exception e1) { }
		        // Check the unique ID we just fetched.  It's possible that we didn't
		        // get anything useful; it's up to manufacturers to set the Android ID
		        // property, and not everybody does it.  If we didn't get anything,
		        // we'll just make up a hard-coded random-ish string and use that as
		        // our starting point.  Of course, if we're using this, our salt will
		        // *NOT* be unique per device, but that's the best we can do.
		    	if (uniqueID == null) uniqueID = SALT;
		    	// If we *did* get a unique ID above, go ahead and concatenate our
		    	// salt string on to the end of it as well.  That should give us
		    	// a salt for our salt.
		    	else uniqueID = uniqueID.concat(SALT);
		        // Now get the unique ID string as raw bytes.  We'll use UTF-8 since
		    	// everything we get should work with that encoding.
		    	byte[] uniqueIDBytes = uniqueID.getBytes(ENCODING); 
		    	// Generate our final salt value by combining the unique ID generated
		    	// above with the random salt stored in the preferences file:
		    	byte[] finalSalt = new byte[uniqueIDBytes.length + salt.length];
		    	for (int i = 0; i < uniqueIDBytes.length; i++) {
		    		finalSalt[i] = uniqueIDBytes[i];
		    	}
		    	for (int j = 0; j < salt.length; j++) {
		    		finalSalt[uniqueIDBytes.length + j] = salt[j];
		    	}
		        // Ideally, we don't want to use the raw ID by itself; that's too
		        // easy to guess.  Rather, let's hash this a few times to give us
		        // something less predictable.
				MessageDigest hasher = MessageDigest.getInstance(SALT_HASH);
				for (int i = 0; i < KEY_ITERATION_COUNT; i++)
					finalSalt = hasher.digest(finalSalt);
				// Now, for good measure, let's obscure our password so we won't be
				// using the value stored in the preferences directly.  We'll
				// concatenate the unique ID generated above into the "encrypted"
				// password, convert that to bytes, and hash it multiple times as
				// well.
				byte[] pwd = password.concat(uniqueID).getBytes(ENCODING);
				for (int i = 0; i < KEY_ITERATION_COUNT; i++)
					pwd = hasher.digest(pwd);
				// From the BC JavaDoc: "Generator for PBE derived keys and IVs as
				// defined by PKCS 5 V2.0 Scheme 2. This generator uses a SHA-1
				// HMac as the calculation function."  This is apparently a standard.
				PKCS5S2ParametersGenerator generator =
					new PKCS5S2ParametersGenerator();
				// Initialize the generator with our password and salt.  Note the
				// iteration count value.  Examples I found around the Net set this
				// as a hex value, but I'm not sure why advantage there is to that.
				// I changed it to decimal for clarity.  Ideally, this should be a
				// very large number, but experiments seem to show that setting this
				// too high makes the program sluggish.  We'll stick to the same
				// key iteration count we've been using.
				generator.init(pwd, finalSalt, KEY_ITERATION_COUNT);
				// Generate our parameters.  We want to do AES-256, so we'll set
				// that as our key size.  That also implies a 128-bit IV.
				iv = ((ParametersWithIV)generator.generateDerivedParameters(KEY_SIZE,
						IV_SIZE));
				// Create our AES (i.e. Rijndael) engine and create the actual
				// cipher object from it.  We'll use CBC padding.
				RijndaelEngine engine = new RijndaelEngine();
				cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine));
			// If the password was not set, we'll null out the cipher and IV to
			// prevent encryption from taking place:
			} else {
				cipher = null;
				iv = null;
			}
		}
		// If anything blew up, null out the cipher and IV as well:
		catch (Exception e)
		{
			cipher = null;
			iv = null;
		}
    }
}
