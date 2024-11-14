package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.util.Pack;

/**
 * implementation of SHA-1 as outlined in "Handbook of Applied Cryptography", pages 346 - 349.
 *
 * It is interesting to ponder why the, apart from the extra IV, the other difference here from MD5
 * is the "endienness" of the word processing!
 */
public class SHA1Digest
    extends GeneralDigest
{
    private static final int    DIGEST_LENGTH = 20;

    private int     H1, H2, H3, H4, H5;

    private int[]   X = new int[80];
    private int     xOff;

    protected void processWord(
        byte[]  in,
        int     inOff)
    {
        // Note: Inlined for performance
//        X[xOff] = Pack.bigEndianToInt(in, inOff);
        int n = in[  inOff] << 24;
        n |= (in[++inOff] & 0xff) << 16;
        n |= (in[++inOff] & 0xff) << 8;
        n |= (in[++inOff] & 0xff);
        X[xOff] = n;

        if (++xOff == 16)
        {
            processBlock();
        }        
    }

    //
    // Additive constants
    //
    private static final int    Y1 = 0x5a827999;
    private static final int    Y2 = 0x6ed9eba1;
    private static final int    Y3 = 0x8f1bbcdc;
    private static final int    Y4 = 0xca62c1d6;
}




