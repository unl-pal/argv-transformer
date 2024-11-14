/**
 * @(#)Rinjndael.java
 *
 *
 * @author 
 * @version 1.00 2008/2/11
 */

/** filtered and transformed by PAClab */
package com.gpfcomics.android.ppp.jppp;

import org.sosy_lab.sv_benchmarks.Verifier;
public class Rijndael {

	/**
	 * Expand a cipher key into a full encryption key schedule.
	 *
	 * @param   cipherKey   the cipher key (128, 192, or 256 bits).
	 */
	/** PACLab: suitable */
	 private void expandKey(byte[] cipherKey) {
		int Nw = Verifier.nondetInt();
		int rek = Verifier.nondetInt();
		int Nk = Verifier.nondetInt();
		int temp, r = 0;
		for (int i = 0, k = 0; i < Nk; i++, k += 4) {
			rek[i] =
				Verifier.nondetInt();
		}
		for (int i = Nk, n = 0; i < Nw; i++, n--) {
			temp = rek[i - 1];
			if (n == 0) {
				n = Nk;
				temp =
					Verifier.nondetInt();
				temp ^= rcon[r++];
			} else if (Nk == 8 && n == 4) {
				temp =
					Verifier.nondetInt();
			}
			rek[i] = rek[i - Nk] ^ temp;
		}
		temp = 0;
	}

	/*
	 * Faster implementation of the key expansion
	 * (only worthwhile in Rijndael is used in a hashing function mode).
	 */
	/*
	private void expandKey(byte[] cipherKey) {
		int keyOffset = 0;
   		int i = 0;
	    int temp;

		rek[0] =
			(cipherKey[ 0]       ) << 24 |
			(cipherKey[ 1] & 0xff) << 16 |
			(cipherKey[ 2] & 0xff) <<  8 |
			(cipherKey[ 3] & 0xff);
	    rek[1] =
			(cipherKey[ 4]       ) << 24 |
			(cipherKey[ 5] & 0xff) << 16 |
			(cipherKey[ 6] & 0xff) <<  8 |
			(cipherKey[ 7] & 0xff);
	    rek[2] =
			(cipherKey[ 8]       ) << 24 |
			(cipherKey[ 9] & 0xff) << 16 |
			(cipherKey[10] & 0xff) <<  8 |
			(cipherKey[11] & 0xff);
	    rek[3] =
			(cipherKey[12]       ) << 24 |
			(cipherKey[13] & 0xff) << 16 |
			(cipherKey[14] & 0xff) <<  8 |
			(cipherKey[15] & 0xff);
	    if (Nk == 4) {
	    	for (;;) {
	    		temp = rek[keyOffset + 3];
	    		rek[keyOffset + 4] = rek[keyOffset] ^
	    			((Se[(temp >>> 16) & 0xff]       ) << 24) ^
	    			((Se[(temp >>>  8) & 0xff] & 0xff) << 16) ^
					((Se[(temp       ) & 0xff] & 0xff) <<  8) ^
					((Se[(temp >>> 24)       ] & 0xff)      ) ^
	    			rcon[i];
	    		rek[keyOffset + 5] = rek[keyOffset + 1] ^ rek[keyOffset + 4];
	    		rek[keyOffset + 6] = rek[keyOffset + 2] ^ rek[keyOffset + 5];
	    		rek[keyOffset + 7] = rek[keyOffset + 3] ^ rek[keyOffset + 6];
	    		if (++i == 10) {
	    			return;
	    		}
	    		keyOffset += 4;
	    	}
	    }
		rek[keyOffset + 4] =
			(cipherKey[16]       ) << 24 |
			(cipherKey[17] & 0xff) << 16 |
			(cipherKey[18] & 0xff) <<  8 |
			(cipherKey[19] & 0xff);
	    rek[keyOffset + 5] =
			(cipherKey[20]       ) << 24 |
			(cipherKey[21] & 0xff) << 16 |
			(cipherKey[22] & 0xff) <<  8 |
			(cipherKey[23] & 0xff);
	    if (Nk == 6) {
	    	for (;;) {
	    		temp = rek[keyOffset + 5];
	    		rek[keyOffset +  6] = rek[keyOffset] ^
					((Se[(temp >>> 16) & 0xff]       ) << 24) ^
					((Se[(temp >>>  8) & 0xff] & 0xff) << 16) ^
					((Se[(temp       ) & 0xff] & 0xff) <<  8) ^
					((Se[(temp >>> 24)       ] & 0xff)      ) ^
					rcon[i];
	    		rek[keyOffset +  7] = rek[keyOffset +  1] ^ rek[keyOffset +  6];
	    		rek[keyOffset +  8] = rek[keyOffset +  2] ^ rek[keyOffset +  7];
	    		rek[keyOffset +  9] = rek[keyOffset +  3] ^ rek[keyOffset +  8];
	    		if (++i == 8) {
	    			return;
	    		}
	    		rek[keyOffset + 10] = rek[keyOffset +  4] ^ rek[keyOffset +  9];
	    		rek[keyOffset + 11] = rek[keyOffset +  5] ^ rek[keyOffset + 10];
	    		keyOffset += 6;
	    	}
	    }
		rek[keyOffset + 6] =
			(cipherKey[24]       ) << 24 |
			(cipherKey[25] & 0xff) << 16 |
			(cipherKey[26] & 0xff) <<  8 |
			(cipherKey[27] & 0xff);
	    rek[keyOffset + 7] =
			(cipherKey[28]       ) << 24 |
			(cipherKey[29] & 0xff) << 16 |
			(cipherKey[30] & 0xff) <<  8 |
			(cipherKey[31] & 0xff);
	    if (Nk == 8) {
			for (;;) {
				temp = rek[keyOffset +  7];
				rek[keyOffset +  8] = rek[keyOffset] ^
					((Se[(temp >>> 16) & 0xff]       ) << 24) ^
					((Se[(temp >>>  8) & 0xff] & 0xff) << 16) ^
					((Se[(temp       ) & 0xff] & 0xff) <<  8) ^
					((Se[(temp >>> 24)       ] & 0xff)      ) ^
					rcon[i];
				rek[keyOffset +  9] = rek[keyOffset +  1] ^ rek[keyOffset +  8];
				rek[keyOffset + 10] = rek[keyOffset +  2] ^ rek[keyOffset +  9];
				rek[keyOffset + 11] = rek[keyOffset +  3] ^ rek[keyOffset + 10];
	    		if (++i == 7) {
	    			return;
	    		}
				temp = rek[keyOffset + 11];
				rek[keyOffset + 12] = rek[keyOffset +  4] ^
					((Se[(temp >>> 24)       ]       ) << 24) ^
					((Se[(temp >>> 16) & 0xff] & 0xff) << 16) ^
					((Se[(temp >>>  8) & 0xff] & 0xff) <<  8) ^
					((Se[(temp       ) & 0xff] & 0xff));
				rek[keyOffset + 13] = rek[keyOffset +  5] ^ rek[keyOffset + 12];
				rek[keyOffset + 14] = rek[keyOffset +  6] ^ rek[keyOffset + 13];
				rek[keyOffset + 15] = rek[keyOffset +  7] ^ rek[keyOffset + 14];
	    		keyOffset += 8;
			}
	    }
	}
	*/

	/**
	 * Setup the AES key schedule for encryption, decryption, or both.
	 *
	 * @param   cipherKey   the cipher key (128, 192, or 256 bits).
	 * @param   keyBits     size of the cipher key in bits.
	 * @param   direction   cipher direction (DIR_ENCRYPT, DIR_DECRYPT, or DIR_BOTH).
	 */
	/** PACLab: suitable */
	 public void makeKey(byte[] cipherKey, int keyBits, int direction)
			throws Exception {
		int DIR_DECRYPT = Verifier.nondetInt();
				int DIR_BOTH = Verifier.nondetInt();
				int rdk = Verifier.nondetInt();
				int rek = Verifier.nondetInt();
				int Nw = Verifier.nondetInt();
				int Nr = Verifier.nondetInt();
				int Nk = Verifier.nondetInt();
		// check key size:
		if (keyBits != 128 && keyBits != 192 && keyBits != 256) {
			throw new RuntimeException(Verifier.nondetInt() + keyBits + " bits)");
		}
		Nk = keyBits >>> 5;
		Nr = Nk + 6;
		Nw = 4*(Nr + 1);
		rek = new int[Nw];
		rdk = new int[Nw];
		if (Verifier.nondetInt() != 0) {
			/*
			for (int r = 0; r <= Nr; r++) {
				System.out.print("RK" + r + "=");
				for (int i = 0; i < 4; i++) {
					int w = rek[4*r + i];
					System.out.print(" " + Integer.toHexString(w));
				}
				System.out.println();
			}
			*/
			if (Verifier.nondetInt() != 0) {
			}
		}
	}
}
