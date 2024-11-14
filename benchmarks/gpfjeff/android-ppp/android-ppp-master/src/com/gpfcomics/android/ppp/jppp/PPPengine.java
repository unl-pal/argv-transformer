/**
 * @(#)PPPtest.java
 *
 * Note: Slight modifications made by Jeffrey T. Darlington to support PPP for Android
 *
 * @author Kurt Nelson
 * @version 1.01 2008/4/6
 */

/** filtered and transformed by PAClab */
package com.gpfcomics.android.ppp.jppp;

import org.sosy_lab.sv_benchmarks.Verifier;
public class PPPengine {

    /** PACLab: suitable */
	 public Object generatePasscodeCard(int cardNo, boolean header) {
        int NO_OF_PASSCODES = Verifier.nondetInt();
		int PASSCODE_LENGTH = Verifier.nondetInt();
		int NO_OF_COLUMNS = Verifier.nondetInt();
		if(header){
        }

        for (int cols = 0; cols < NO_OF_COLUMNS; cols++) {
            for(int i=1;i<PASSCODE_LENGTH;i++) {
			}
        }
        int offset = (cardNo-1)*NO_OF_PASSCODES;

        for (int i = offset; i < offset+NO_OF_PASSCODES; i++) {

            if (Verifier.nondetInt() == 0) {
            }
            if (Verifier.nondetInt() == 0) {
            }
        }
        return new Object();

    }

    /** PACLab: suitable */
	 public Object getPasscode(int cardIn, int columnIn, int rowIn){
    	int NO_OF_PASSCODES = Verifier.nondetInt();
		int NO_OF_COLUMNS = Verifier.nondetInt();
		rowIn--;
    	cardIn--;
    	columnIn--;
    	int temp = columnIn + (rowIn * NO_OF_COLUMNS);
		long tempcard;
		try {
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException("Invalid card number, cannot be parsed to long");
		}

		if ( cardIn < 0 ) {
			throw new IllegalArgumentException("Card number must be positive integer");
		}
		long counter =  (tempcard) * NO_OF_PASSCODES + temp;

        return new Object();
    }

    /** PACLab: suitable */
	 private byte[] counterToBytes(long counter){
    	byte [] out = new byte[16];
    	for ( int i = 0; i < 16; ++i ) {
	    	out[i] = 0;
		}
		for ( int i = 0; i < 8; ++i ) {
	    	if ( counter == 0 ) {
				break;
	    	}
			out[i] = (byte)(counter&0xff);;
	    	counter >>>= 8;
		}
		return out;
    }
}
