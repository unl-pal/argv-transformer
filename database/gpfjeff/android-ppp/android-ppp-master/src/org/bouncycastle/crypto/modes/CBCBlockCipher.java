package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/**
 * implements Cipher-Block-Chaining (CBC) mode on top of a simple cipher.
 */
public class CBCBlockCipher
    implements BlockCipher
{
    private byte[]          IV;
    private byte[]          cbcV;
    private byte[]          cbcNextV;

    private int             blockSize;
    private BlockCipher     cipher = null;
    private boolean         encrypting;

    /**
     * Do the appropriate chaining step for CBC mode encryption.
     *
     * @param in the array containing the data to be encrypted.
     * @param inOff offset into the in array the data starts at.
     * @param out the array the encrypted data will be copied into.
     * @param outOff the offset into the out array the output will start at.
     * @exception DataLengthException if there isn't enough data in in, or
     * space in out.
     * @exception IllegalStateException if the cipher isn't initialised.
     * @return the number of bytes processed and produced.
     */
    private int encryptBlock(
        byte[]      in,
        int         inOff,
        byte[]      out,
        int         outOff)
        throws DataLengthException, IllegalStateException
    {
        if ((inOff + blockSize) > in.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        /*
         * XOR the cbcV and the input,
         * then encrypt the cbcV
         */
        for (int i = 0; i < blockSize; i++)
        {
            cbcV[i] ^= in[inOff + i];
        }

        int length = cipher.processBlock(cbcV, 0, out, outOff);

        /*
         * copy ciphertext to cbcV
         */
        System.arraycopy(out, outOff, cbcV, 0, cbcV.length);

        return length;
    }

    /**
     * Do the appropriate chaining step for CBC mode decryption.
     *
     * @param in the array containing the data to be decrypted.
     * @param inOff offset into the in array the data starts at.
     * @param out the array the decrypted data will be copied into.
     * @param outOff the offset into the out array the output will start at.
     * @exception DataLengthException if there isn't enough data in in, or
     * space in out.
     * @exception IllegalStateException if the cipher isn't initialised.
     * @return the number of bytes processed and produced.
     */
    private int decryptBlock(
        byte[]      in,
        int         inOff,
        byte[]      out,
        int         outOff)
        throws DataLengthException, IllegalStateException
    {
        if ((inOff + blockSize) > in.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        System.arraycopy(in, inOff, cbcNextV, 0, blockSize);

        int length = cipher.processBlock(in, inOff, out, outOff);

        /*
         * XOR the cbcV and the output
         */
        for (int i = 0; i < blockSize; i++)
        {
            out[outOff + i] ^= cbcV[i];
        }

        /*
         * swap the back up buffer into next position
         */
        byte[]  tmp;

        tmp = cbcV;
        cbcV = cbcNextV;
        cbcNextV = tmp;

        return length;
    }
}
