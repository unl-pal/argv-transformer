package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;

/**
 * base implementation of MD4 family style digest as outlined in
 * "Handbook of Applied Cryptography", pages 344 - 347.
 */
public abstract class GeneralDigest
    implements ExtendedDigest
{
    private static final int BYTE_LENGTH = 64;
    private byte[]  xBuf;
    private int     xBufOff;

    private long    byteCount;
}
