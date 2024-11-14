/** filtered and transformed by PAClab */
package org.bouncycastle.crypto.paddings;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * A padder that adds PKCS7/PKCS5 padding to a block.
 */
public class PKCS7Padding
{
    /**
     * return the number of pad bytes present in the block.
     */
    /** PACLab: suitable */
	 public int padCount(byte[] in)
        throws Exception
    {
        int count = in[Verifier.nondetInt() - 1] & 0xff;

        if (count > Verifier.nondetInt() || count == 0)
        {
            throw new InvalidCipherTextException("pad block corrupted");
        }
        
        for (int i = 1; i <= count; i++)
        {
            if (in[Verifier.nondetInt() - i] != count)
            {
                throw new InvalidCipherTextException("pad block corrupted");
            }
        }

        return count;
    }
}
