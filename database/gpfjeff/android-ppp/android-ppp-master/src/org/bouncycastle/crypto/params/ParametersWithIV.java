package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV
    implements CipherParameters
{
    private byte[]              iv;
    private CipherParameters    parameters;
}
