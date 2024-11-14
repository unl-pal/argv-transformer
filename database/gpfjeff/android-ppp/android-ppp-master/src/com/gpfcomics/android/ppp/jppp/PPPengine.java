/**
 * @(#)PPPtest.java
 *
 * Note: Slight modifications made by Jeffrey T. Darlington to support PPP for Android
 *
 * @author Kurt Nelson
 * @version 1.01 2008/4/6
 */

package com.gpfcomics.android.ppp.jppp;
public class PPPengine {

    /**
     * @param args the command line arguments
     */
    private static int NO_OF_COLUMNS = 7;
    private static int NO_OF_ROWS = 10;
    private static int PASSCODE_LENGTH = 4;
    private static char[] ALPHABET = {'!','#','%','+','2','3','4','5','6','7','8','9',':','=','?','@','A','B','C','D','E','F','G','H','J','K','L','M','N','P','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static int NO_OF_PASSCODES = 70;
    private static char[] COLUMNS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private String sequenceKey;

    public String generatePasscodeCard(int cardNo, boolean header) {
        String c = "";

        if(header){
        	c = "C" + cardNo;
        }

        c += "\t";
        for (int cols = 0; cols < NO_OF_COLUMNS; cols++) {
            c += COLUMNS[cols];
            for(int i=1;i<PASSCODE_LENGTH;i++)
            	c += " ";
            c += "\t";
        }
        c += "\n";

        int offset = (cardNo-1)*NO_OF_PASSCODES;

        for (int i = offset; i < offset+NO_OF_PASSCODES; i++) {

            if (i % NO_OF_COLUMNS == 0) {
                c += (((i-offset) / NO_OF_COLUMNS) + 1) + "\t";
            }
            c += getPasscode(new Long(i));
            c += "\t";

            if ((i + 1) % NO_OF_COLUMNS == 0) {
                c += "\n";
            }
        }
        return c;

    }

    public String getPasscode(int cardIn, int columnIn, int rowIn){
    	rowIn--;
    	cardIn--;
    	columnIn--;
    	int temp = columnIn + (rowIn * NO_OF_COLUMNS);
		long tempcard;
		try {
	    	tempcard = new Long(cardIn);
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException("Invalid card number, cannot be parsed to long");
		}

		if ( cardIn < 0 ) {
			throw new IllegalArgumentException("Card number must be positive integer");
		}
		long counter =  (tempcard) * NO_OF_PASSCODES + temp;

        return getPasscode(counter);
    }

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
