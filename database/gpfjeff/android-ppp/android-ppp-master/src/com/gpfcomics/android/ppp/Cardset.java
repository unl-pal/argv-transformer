/* Cardset.java
 * 
 * PROGRAMMER:    Jeffrey T. Darlington
 * DATE:          February 23, 2011
 * PROJECT:       Perfect Paper Passwords for Android
 * ANDROID V.:	  1.1
 * 
 * The Cardset class represents the defining parameters and current basic state of
 * a given set of cards within Perfect Paper Passwords.  This class contains all
 * the necessary parameters to define a given set of cards, such as its sequence key,
 * alphabet, number of rows and columns on each card, and the passcode length.  It
 * also defines the current state of the card set within our application, giving it
 * a name, an internal ID for the database, and the last card that we used.
 * 
 * To help put all the validation code in one place, there is a series of public static
 * methods near the bottom to validate strings and, in some cases, the base primitive
 * types for each parameter of the card set.  This way, UI elements such as the New
 * Card Activity can call on the same validation code as this class uses internally.
 * These all return Boolean values which, while not giving detailed feedback on why the
 * validation failed, does give a simple pass/fail check. 
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

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * The Cardset class represents the defining parameters and current basic state of
 * a given set of cards within Perfect Paper Passwords.  This class contains all
 * the necessary parameters to define a given set of cards, such as its sequence key,
 * alphabet, number of rows and columns on each card, and the passcode length.  It
 * also defines the current state of the card set within our application, giving it
 * a name, an internal ID for the database, and the last card that we used.
 * @author Jeffrey T. Darlington
 * @version 1.0
 * @since 1.0
 */
public class Cardset {
	
    /* ####### Private Constants ####### */
    
	/** A regular expression for determining whether or not a string is to be
	 *  considered empty.  If the string has no characters or consists entirely of
	 *  white space, it is considered empty.  Note that testing for a null value
	 *  will be an separate check. */
	private static final String EMPTY_STRING_REGEX = "^\\s*$";
	
	/** A regular expression for identifying positive integers.  There must be no
	 *  white space, alphabetic characters, or symbols; only one or more digits.
	 *  Note that this does not test for null strings, which must be a separate
	 *  check, nor does it currently test to see if the first digit is a zero. */
	private static final String POS_INTEGER_REGEX = "^\\d+$";
	
	/** A regular expression to identify whether or not the tested string (which we
	 *  assume has passed the POS_INTEGER_REGEX test above) contains leading zeros. */
	private static final String LEADING_ZEROS_REGEX = "^0+";
	
    /* ####### Public Constants ####### */
    
	/** The official internal ID number for card sets that have not been saved to
	 *  the database.  This will be the default ID for all cards until they have
	 *  been saved and assigned a new ID by the database. */
	public static final long NOID = -1L;
	/** The number of the first card in the sequence.  Card numbers may not be less
	 *  than this value, and all new card sets will start with this card number by
	 *  default. */
	public static final int FIRST_CARD = 1;
	/** In theory, a card set may have an infinite number of cards.  In reality, we're
	 *  using signed integers, meaning we can have Integer.MAX_VALUE cards.  While it
	 *  is extremely unlikely anyone will ever hit this value, we'll cap it here as
	 *  an extra layer of security.  A card set cannot exceed this number of cards,
	 *  and any attempt to move beyond it will fail. */
	public static final int FINAL_CARD = Integer.MAX_VALUE;
	/** The default number of columns per card.  This should match the value of the
	 *  PPP demonstration page on the GRC site. */
	public static final int DEFAULT_COLUMNS = 7;
	/** The default number of rows per card.  This should match the value of the
	 *  PPP demonstration page on the GRC site. */
	public static final int DEFAULT_ROWS = 10;
	/** The default passcode length.  This should match the value of the
	 *  PPP demonstration page on the GRC site. */
	public static final int DEFAULT_PASSCODE_LENGTH = 4;
	/** The default alphabet.  This should match the "standard and conservative"
	 *  64-character alphabet defined on the PPP demonstration page on the GRC site. */
	public static final String DEFAULT_ALPHABET =
		"!#%+23456789:=?@ABCDEFGHJKLMNPRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
	/** The "visually aggressive" 88-character alphabet defined on the PPP
	 *  demonstration page on the GRC site. */
	public static final String AGGRESSIVE_ALPHABET =
		"!\"#$%&'()*+,-./23456789:;<=>?@ABCDEFGHJKLMNOPRSTUVWXYZ[\\]^_abcdefghijkmnopqrstuvwxyz{|}~";
	/** A regular expression pattern used for testing sequence keys values to
	 *  make sure they are valid.  Sequence keys must be 64-character hex strings. */
	public static final String SEQUENCE_KEY_REGEX = "^[0-9a-fA-F]{64}$";
	/** The maximum practical width in displayed characters that will fit into
	 *  portrait orientation on a typical Android device screen.  Any product of the
	 *  number of columns and the passcode length that is greater than this should
	 *  force landscape orientation in the card view display. */
	public static final int MAX_PORTRAIT_WIDTH = 34; 
	/** The maximum practical width in displayed characters of any card.  The product
	 *  of the number of columns and the passcode length must be less than or equal
	 *  to this value or we won't be able to display it. */
	public static final int MAX_CARD_WIDTH = 60;
	
    /* ####### Private Members ####### */

	/** The card set's internal ID number */
	private long cardsetId = NOID;
	/** The card set's user-assigned name */
	private String name = "Unnamed Card Set";
	/** The number of columns per card */
	private int numColumns = DEFAULT_COLUMNS;
	/** The number of rows per card */
    private int numRows = DEFAULT_ROWS;
	/** The passcode length */
    private int passcodeLength = DEFAULT_PASSCODE_LENGTH;
	/** The alphabet for this card set */
    private String alphabet = DEFAULT_ALPHABET;
	/** The card set sequence key as a hexadecimal string */
    private String sequenceKey = null;
	/** The current (i.e. last) card number */
    private int lastCard = FIRST_CARD;
  
}
