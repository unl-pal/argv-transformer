import org.sosy_lab.sv_benchmarks.Verifier;

public  class Main {
    public static int read(byte[] b,  int count) throws Exception {
        int windowUpdateThreshold = Verifier.nondetInt();
        int unacknowledgedBytes = Verifier.nondetInt();
        int limit = Verifier.nondetInt();

        if(limit >= 0) {
            int pos = Verifier.nondetInt();
            if (pos == -1) {
                return -1;
            }
            if (pos >= 0) {
                assert (pos >= 0);
                int copied = 0;
                int bytesToCopy = Verifier.nondetInt();
                if(bytesToCopy >= 0) {
                    //         drain from [pos..buffer.length)
                    if (limit <= pos) {
                        pos += bytesToCopy;
                        copied += bytesToCopy;
                        if (pos == Verifier.nondetInt()) {
                            pos = 0;
                        }
                    }
                    //drain from [pos..limit)
                    if (copied < count) {
                        pos += bytesToCopy;
                        copied += bytesToCopy;
                    }
                }
                if (pos == limit) {
                    pos = -1;
                    limit = 0;
                }
                assert (copied >= 0) ; // make note in document
                return copied;
            }
            else {
                return -1;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        int n = Verifier.nondetInt();
        if (n >= 0){
            byte[] b = new byte[n];
            int count =  Verifier.nondetInt();
            if(count > 0){
                try{
                    int copies = read(b,count);

                }

                catch (Exception e){
                    System.out.println(e);
                }

            }
        }
    }
}