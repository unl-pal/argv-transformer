package org.bouncycastle.crypto.paddings;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ParametersWithRandom;

/**
 * A wrapper class that allows block ciphers to be used to process data in
 * a piecemeal fashion with padding. The PaddedBufferedBlockCipher
 * outputs a block only when the buffer is full and more data is being added,
 * or on a doFinal (unless the current block in the buffer is a pad block).
 * The default padding mechanism used is the one outlined in PKCS5/PKCS7.
 */
public class PaddedBufferedBlockCipher
    extends BufferedBlockCipher
{
    BlockCipherPadding  padding;

    /**
     * return the minimum size of the output buffer required for an update
     * plus a doFinal with an input of len bytes.
     *
     * @param len the length of the input.
     * @return the space required to accommodate a call to update and doFinal
     * with len bytes of input.
     */
    public int getOutputSize(
        int len)
    {
        int total       = len + bufOff;
        int leftOver    = total % buf.length;

        if (leftOver == 0)
        {
            if (forEncryption)
            {
                return total + buf.length;
            }

            return total;
        }

        return total - leftOver + buf.length;
    }

    /**
     * return the size of the output buffer required for an update 
     * an input of len bytes.
     *
     * @param len the length of the input.
     * @return the space required to accommodate a call to update
     * with len bytes of input.
     */
    public int getUpdateOutputSize(
        int len)
    {
        int total       = len + bufOff;
        int leftOver    = total % buf.length;

        if (leftOver == 0)
        {
            return total - buf.length;
        }

        return total - leftOver;
    }

    /**
     * process an array of bytes, producing output if necessary.
     *
     * @param in the input byte array.
     * @param inOff the offset at which the input data starts.
     * @param len the number of bytes to be copied out of the input array.
     * @param out the space for any output that might be produced.
     * @param outOff the offset from which the output will be copied.
     * @return the number of output bytes copied to out.
     * @exception DataLengthException if there isn't enough space in out.
     * @exception IllegalStateException if the cipher isn't initialised.
     */
    public int processBytes(
        byte[]      in,
        int         inOff,
        int         len,
        byte[]      out,
        int         outOff)
        throws DataLengthException, IllegalStateException
    {
        if (len < 0)
        {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }

        int blockSize   = getBlockSize();
        int length      = getUpdateOutputSize(len);
        
        if (length > 0)
        {
            if ((outOff + length) > out.length)
            {
                throw new DataLengthException("output buffer too short");
            }
        }

        int resultLen = 0;
        int gapLen = buf.length - bufOff;

        if (len > gapLen)
        {
            System.arraycopy(in, inOff, buf, bufOff, gapLen);

            resultLen += cipher.processBlock(buf, 0, out, outOff);

            bufOff = 0;
            len -= gapLen;
            inOff += gapLen;

            while (len > buf.length)
            {
                resultLen += cipher.processBlock(in, inOff, out, outOff + resultLen);

                len -= blockSize;
                inOff += blockSize;
            }
        }

        System.arraycopy(in, inOff, buf, bufOff, len);

        bufOff += len;

        return resultLen;
    }
}
