/** filtered and transformed by PAClab */
package org.bouncycastle.crypto.generators;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Generator for PBE derived keys and ivs as defined by PKCS 5 V2.0 Scheme 2.
 * This generator uses a SHA-1 HMac as the calculation function.
 * <p>
 * The document this implementation is based on can be found at
 * <a href=http://www.rsasecurity.com/rsalabs/pkcs/pkcs-5/index.html>
 * RSA's PKCS5 Page</a>
 */
public class PKCS5S2ParametersGenerator
{
    /** PACLab: suitable */
	 private void F(
        byte[]  P,
        byte[]  S,
        int     c,
        byte[]  iBuf,
        byte[]  out,
        int     outOff)
    {
        byte[]              state = new byte[hMac.getMacSize()];
        if (S != null)
        {
        }

        if (c == 0)
        {
            throw new IllegalArgumentException("iteration count must be at least 1.");
        }
        
        for (int count = 1; count < c; count++)
        {
            for (int j = 0; j != Verifier.nondetInt(); j++)
            {
                out[outOff + j] ^= state[j];
            }
        }
    }
}
