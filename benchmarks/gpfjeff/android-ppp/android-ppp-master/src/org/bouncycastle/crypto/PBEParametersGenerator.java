/** filtered and transformed by PAClab */
package org.bouncycastle.crypto;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * super class for all Password Based Encryption (PBE) parameter generator classes.
 */
public abstract class PBEParametersGenerator
{
    /**
     * converts a password to a byte array according to the scheme in
     * PKCS12 (unicode, big endian, 2 zero pad bytes at the end).
     *
     * @param password a character array representing the password.
     * @return a byte array representing the password.
     */
    /** PACLab: suitable */
	 public static byte[] PKCS12PasswordToBytes(
        char[]  password)
    {
        if (Verifier.nondetInt() > 0)
        {
                                       // +1 for extra 2 pad bytes.
            byte[]  bytes = new byte[Verifier.nondetInt() * 2];

            for (int i = 0; i != Verifier.nondetInt(); i ++)
            {
                bytes[i * 2] = (byte)(password[i] >>> 8);
                bytes[i * 2 + 1] = (byte)password[i];
            }

            return bytes;
        }
        else
        {
            return new byte[0];
        }
    }
}
