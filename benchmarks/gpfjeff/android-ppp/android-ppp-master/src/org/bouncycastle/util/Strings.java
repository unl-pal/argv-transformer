package org.bouncycastle.util;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

/** filtered by PAClab */
 public final class Strings
{
    public static String fromUTF8ByteArray(byte[] bytes)
    {
        int i = 0;
        int length = 0;

        while (i < bytes.length)
        {
            length++;
            if ((bytes[i] & 0xf0) == 0xf0)
            {
                // surrogate pair
                length++;
                i += 4;
            }
            else if ((bytes[i] & 0xe0) == 0xe0)
            {
                i += 3;
            }
            else if ((bytes[i] & 0xc0) == 0xc0)
            {
                i += 2;
            }
            else
            {
                i += 1;
            }
        }

        char[] cs = new char[length];

        i = 0;
        length = 0;

        while (i < bytes.length)
        {
            char ch;

            if ((bytes[i] & 0xf0) == 0xf0)
            {
                int codePoint = ((bytes[i] & 0x03) << 18) | ((bytes[i+1] & 0x3F) << 12) | ((bytes[i+2] & 0x3F) << 6) | (bytes[i+3] & 0x3F);
                int U = codePoint - 0x10000;
                char W1 = (char)(0xD800 | (U >> 10));
                char W2 = (char)(0xDC00 | (U & 0x3FF));
                cs[length++] = W1;
                ch = W2;
                i += 4;
            }
            else if ((bytes[i] & 0xe0) == 0xe0)
            {
                ch = (char)(((bytes[i] & 0x0f) << 12)
                        | ((bytes[i + 1] & 0x3f) << 6) | (bytes[i + 2] & 0x3f));
                i += 3;
            }
            else if ((bytes[i] & 0xd0) == 0xd0)
            {
                ch = (char)(((bytes[i] & 0x1f) << 6) | (bytes[i + 1] & 0x3f));
                i += 2;
            }
            else if ((bytes[i] & 0xc0) == 0xc0)
            {
                ch = (char)(((bytes[i] & 0x1f) << 6) | (bytes[i + 1] & 0x3f));
                i += 2;
            }
            else
            {
                ch = (char)(bytes[i] & 0xff);
                i += 1;
            }

            cs[length++] = ch;
        }

        return new String(cs);
    }
    
    public static byte[] toUTF8ByteArray(char[] string)
    {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        char[] c = string;
        int i = 0;

        while (i < c.length)
        {
            char ch = c[i];

            if (ch < 0x0080)
            {
                bOut.write(ch);
            }
            else if (ch < 0x0800)
            {
                bOut.write(0xc0 | (ch >> 6));
                bOut.write(0x80 | (ch & 0x3f));
            }
            // surrogate pair
            else if (ch >= 0xD800 && ch <= 0xDFFF)
            {
                // in error - can only happen, if the Java String class has a
                // bug.
                if (i + 1 >= c.length)
                {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }
                char W1 = ch;
                ch = c[++i];
                char W2 = ch;
                // in error - can only happen, if the Java String class has a
                // bug.
                if (W1 > 0xDBFF)
                {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }
                int codePoint = (((W1 & 0x03FF) << 10) | (W2 & 0x03FF)) + 0x10000;
                bOut.write(0xf0 | (codePoint >> 18));
                bOut.write(0x80 | ((codePoint >> 12) & 0x3F));
                bOut.write(0x80 | ((codePoint >> 6) & 0x3F));
                bOut.write(0x80 | (codePoint & 0x3F));
            }
            else
            {
                bOut.write(0xe0 | (ch >> 12));
                bOut.write(0x80 | ((ch >> 6) & 0x3F));
                bOut.write(0x80 | (ch & 0x3F));
            }

            i++;
        }
        
        return bOut.toByteArray();
    }
}
