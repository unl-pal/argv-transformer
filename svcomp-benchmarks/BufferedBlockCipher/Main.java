import org.sosy_lab.sv_benchmarks.Verifier;
/**
 * A wrapper class that allows block ciphers to be used to process data in
 * a piecemeal fashion. The BufferedBlockCipher outputs a block only when the
 * buffer is full and more data is being added, or on a doFinal.
 * <p>
 * Note: in the case where the underlying cipher is either a CFB cipher or an
 * OFB one the last block may not be a multiple of the block size.
 */
public class Main{

    public static int getUpdateOutputSize(int len) {
        boolean pgpCFB = Verifier.nondetBoolean();
        byte[] buf = new byte[len];
        int bufOff = Verifier.nondetInt();
        int updateOutputSize = 0;
        if(bufOff > 0) {
            int total = len + bufOff;
            int leftOver;
            int mod = Verifier.nondetInt();

            if (mod > 0) {
                if (pgpCFB) {
                    leftOver = mod;
                    // Assert that the leftover is non-negative
                } else {
                    leftOver = total % buf.length;
                }

                assert (leftOver == mod || leftOver == total % buf.length);
                updateOutputSize = total - leftOver;
            }

        }
        return updateOutputSize;
    }

    public static int processBytes(
            byte[] in, int inOff, int len, byte[] out, int outOff, int buffLength) throws Exception {

        int bufOff = Verifier.nondetInt();
        byte[] buf = new byte[buffLength];

        assert (inOff >= 0 && len >= 0);
        assert (outOff >= 0);
        assert (buffLength > 0);

        if (len <= 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        assert (len > 0);

        int outputBufferSize = 0;
        int blockSize = Verifier.nondetInt();
        int length = Verifier.nondetInt();
        int bufferSizeSpace = Verifier.nondetInt();
        if (length > 0) {
             outputBufferSize = bufferSizeSpace;

            if ((outOff + length) > outputBufferSize) {
                throw new IllegalArgumentException("output buffer too short");
            }
        }
        assert (length <= 0 || outputBufferSize == bufferSizeSpace);


        int resultLen = 0;
        int gapLen = buffLength - bufOff;
        if (len > gapLen) {
           resultLen += Verifier.nondetInt();
           bufOff = 0;
           len -= gapLen;
           inOff += gapLen;
           if(blockSize >= 0 ) {
               while (len > buffLength) {
                   resultLen += Verifier.nondetInt();
                   len -= blockSize;
                   inOff += blockSize;
               }
           }
       }

       bufOff += len;

       if (bufOff == buffLength) {
           resultLen += Verifier.nondetInt();
           bufOff = 0;
       }


       return resultLen;

    }

    public static void main(String[] args){
        int len = Verifier.nondetInt();
        int inOff = Verifier.nondetInt();
        int outOff = Verifier.nondetInt();
        int inOffLength = Verifier.nondetInt();
        int outLength = Verifier.nondetInt();
        int buffLength = Verifier.nondetInt();
        if(len > 0){
            getUpdateOutputSize(len);
        }
        if(len > 0 && inOff >= 0 && outOff >=0 && inOffLength > 0 && outLength > 0 && buffLength > 0) {
            byte[] in = new byte[inOffLength];
            byte[] out = new byte[outLength];
            try {
                processBytes(in,inOff,len,out,outOff,buffLength);
            }
            catch (Exception e){

            }

        }


    }
}