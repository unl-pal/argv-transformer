/** filtered and transformed by PAClab */
package org.bouncycastle.crypto;

import org.sosy_lab.sv_benchmarks.Verifier;


/**
 * A wrapper class that allows block ciphers to be used to process data in
 * a piecemeal fashion. The BufferedBlockCipher outputs a block only when the
 * buffer is full and more data is being added, or on a doFinal.
 * <p>
 * Note: in the case where the underlying cipher is either a CFB cipher or an
 * OFB one the last block may not be a multiple of the block size.
 */
public class BufferedBlockCipher
{
    /**
     * return the size of the output buffer required for an update 
     * an input of len bytes.
     *
     * @param len the length of the input.
     * @return the space required to accommodate a call to update
     * with len bytes of input.
     */
    /** PACLab: suitable */
	 public int getUpdateOutputSize(
        int len)
    {
        boolean pgpCFB = Verifier.nondetBoolean();
		int bufOff = Verifier.nondetInt();
		int total       = len + bufOff;
        int leftOver;

        if (pgpCFB)
        {
            leftOver    = Verifier.nondetInt();
        }
        else
        {
            leftOver    = total % Verifier.nondetInt();
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
    /** PACLab: suitable */
	 public int processBytes(
        byte[]      in,
        int         inOff,
        int         len,
        byte[]      out,
        int         outOff)
        throws Exception
    {
        int bufOff = Verifier.nondetInt();
		if (len < 0)
        {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }

        int blockSize   = Verifier.nondetInt();
        int length      = Verifier.nondetInt();
        
        if (length > 0)
        {
            if ((outOff + length) > Verifier.nondetInt())
            {
                throw new DataLengthException("output buffer too short");
            }
        }

        int resultLen = 0;
        int gapLen = Verifier.nondetInt() - bufOff;

        if (len > gapLen)
        {
            resultLen += Verifier.nondetInt();

            bufOff = 0;
            len -= gapLen;
            inOff += gapLen;

            while (len > Verifier.nondetInt())
            {
                resultLen += Verifier.nondetInt();

                len -= blockSize;
                inOff += blockSize;
            }
        }

        bufOff += len;

        if (bufOff == Verifier.nondetInt())
        {
            resultLen += Verifier.nondetInt();
            bufOff = 0;
        }

        return resultLen;
    }
}
