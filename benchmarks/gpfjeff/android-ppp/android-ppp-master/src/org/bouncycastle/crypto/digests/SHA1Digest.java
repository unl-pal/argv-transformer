/** filtered and transformed by PAClab */
package org.bouncycastle.crypto.digests;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * implementation of SHA-1 as outlined in "Handbook of Applied Cryptography", pages 346 - 349.
 *
 * It is interesting to ponder why the, apart from the extra IV, the other difference here from MD5
 * is the "endienness" of the word processing!
 */
public class SHA1Digest
{
    /** PACLab: suitable */
	 protected void processWord(
        byte[]  in,
        int     inOff)
    {
        int xOff = Verifier.nondetInt();
		// Note: Inlined for performance
//        X[xOff] = Pack.bigEndianToInt(in, inOff);
        int n = in[  inOff] << 24;
        n |= Verifier.nondetInt() << 16;
        n |= Verifier.nondetInt() << 8;
        n |= (in[++inOff] & 0xff);
        X[xOff] = n;

        if (++xOff == 16)
        {
        }        
    }
}




