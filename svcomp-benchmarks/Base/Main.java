import org.sosy_lab.sv_benchmarks.Verifier;


public class Main{
    public static byte[] encode(byte[] source, int off, int len, byte[] alphabet, int maxLineLength) {

        assert alphabet.length <= 64;
        assert source.length >= off + len;

        assert (len > 0);
        byte newLine = '\n';
        int lenDiv3 = (len + 2) ; // ceil(len / 3)
        assert (lenDiv3 == (len +2));
        int len43 = lenDiv3 * 4;
        byte[] outBuff = new byte[len43 + (len43 / maxLineLength)];// Main 4:3  // New lines
        // this is getting success but it should fail?
        assert (outBuff.length == len43 + (len43 / maxLineLength));

        int d = 0;
        int e = 0;
        int len2 = len - 2;
        int lineLength = 0;

        for (; d < len2; d += 3, e += 4) {

            // The following block of code is the same as
            // encode3to4( source, d + off, 3, outBuff, e, alphabet );
            // but inlined for faster encoding (~20% improvement)
            int inBuff = Verifier.nondetInt();
            outBuff[e] = alphabet[(inBuff >>> 18)];
            outBuff[e + 1] = alphabet[Verifier.nondetInt() & 0x3f];
            outBuff[e + 2] = alphabet[Verifier.nondetInt() & 0x3f];
            outBuff[e + 3] = alphabet[(inBuff) & 0x3f];

            lineLength += 4;
            if (lineLength == maxLineLength) {
                outBuff[e + 4] = newLine;
                e++;
                lineLength = 0;
            } // end if: end of line
        } // end for: each piece of array


        if (d < len) {
            lineLength += 4;
            if (lineLength == maxLineLength) {
                // Add a last newline
                outBuff[e + 4] = newLine;
                e++;
            }
            e += 4;
        }
        return outBuff;
    }

    public static void main(String[] args){
        int sourceLength = Verifier.nondetInt();
        int off = Verifier.nondetInt();
        int len = Verifier.nondetInt();
        int maxLength = Verifier.nondetInt();
        int alphabetLength = Verifier.nondetInt();
        if(sourceLength > 0 && off >= 0 &&  off <= sourceLength && len > 0
                && len <= sourceLength - off && maxLength >0 && alphabetLength > 0 && alphabetLength <= 64 ){
            byte[] source = new byte[sourceLength];
            byte[] alphabet = new byte[alphabetLength];
            byte[] result = encode(source,off,len,alphabet,maxLength);

        }

    }
}