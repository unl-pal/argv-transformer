/*
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will Google be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, as long as the origin is not misrepresented.
 *
 * Taken from the following Android Developers Blog post:
 * http://android-developers.blogspot.com/2013/08/some-securerandom-thoughts.html
 * 
 * No license was provided with this code, therefore we are assuming this
 * code has been placed into the public domain until otherwise notified.
 */

/** filtered and transformed by PAClab */
package com.gpfcomics.android.ppp;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * Fixes for the output of the default PRNG having low entropy.
 *
 * The fixes need to be applied via {@link #apply()} before any use of Java
 * Cryptography Architecture primitives. A good place to invoke them is in the
 * application's {@code onCreate}.
 */
public final class PRNGFixes {

    /**
     * Applies the fix for OpenSSL PRNG having low entropy. Does nothing if the
     * fix is not needed.
     *
     * @throws SecurityException if the fix is needed but could not be applied.
     */
    /** PACLab: suitable */
	 private static void applyOpenSSLFix() throws Exception {
        int VERSION_CODE_JELLY_BEAN_MR2 = Verifier.nondetInt();
		int VERSION_CODE_JELLY_BEAN = Verifier.nondetInt();
		if ((Verifier.nondetInt() < VERSION_CODE_JELLY_BEAN)
                || (Verifier.nondetInt() > VERSION_CODE_JELLY_BEAN_MR2)) {
            // No need to apply the fix
            return;
        }

        try {
            // Mix output of Linux PRNG into OpenSSL's PRNG
            int bytesRead = Verifier.nondetInt();
            if (bytesRead != 1024) {
                throw new IOException(
                        Verifier.nondetInt()
                                + bytesRead);
            }
        } catch (Exception e) {
            throw new SecurityException("Failed to seed OpenSSL PRNG", e);
        }
    }

    /**
     * {@code Provider} of {@code SecureRandom} engines which pass through
     * all requests to the Linux PRNG.
     */
    private static class LinuxPRNGSecureRandomProvider {
    }

    /**
     * {@link SecureRandomSpi} which passes all requests to the Linux PRNG
     * ({@code /dev/urandom}).
     */
    public static class LinuxPRNGSecureRandom {
    }
}