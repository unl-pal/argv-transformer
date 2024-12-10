import org.sosy_lab.sv_benchmarks.Verifier;
public class Main{
    public static int readInt(int firstByte, int prefixMask) throws Exception {

        int prefix = firstByte & prefixMask;
        assert (prefix == (firstByte & prefixMask));
        if (prefix < prefixMask) {
            return prefix; // This was a single byte value.
        }
        // This is a multibyte value. Read 7 bits at a time.
        int result = prefixMask;
        int shift = 0;
        while (true) {
            int b = Verifier.nondetInt();
            if ((b & 0x80) != 0) { // Ensure b is in [0..255]
                result += (b & 0x7F) << shift;
                shift += 7;
            }
            else {
                result += b << shift; // Last byte.
                break;

            }
        }
        assert(shift % 7 == 0);
        return result;
    }

    public static void main(String[] args){
        int firstByte = Verifier.nondetInt();
        int prefixMask = Verifier.nondetInt();

        try {
            readInt(firstByte, prefixMask);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}