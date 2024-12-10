
import org.sosy_lab.sv_benchmarks.Verifier;


public class Main {

    public static int[] getXLabels(double min, double max, int count) {
        assert (max >= min);
        assert (count > 0);
        int[] result = new int[count];
        final int day = Verifier.nondetInt();
        if(day > 0) {
            assert (day > 0);
           boolean mStartPointSet = Verifier.nondetBoolean();
           double mStartPoint = Verifier.nondetDouble();

           // Ensure mStartPoint is meaningful
           if (!mStartPointSet) {
               mStartPoint = min + day; // Approximation of start point
           }

           // Limit count to avoid too many labels
           if (count > 25) {
               count = 25;
           }
           assert (count <= 25);

           // Calculate the interval between labels
           double cycleMath = (max - min) / count;
           assert (cycleMath >= 0) ;

           // Set an initial cycle based on DAY
           double cycle = (double) day;
           // Adjust the cycle value based on the interval
           if (Verifier.nondetBoolean()) { // Simulate branch condition
               while (cycleMath < cycle / 2) {
                   cycle = cycle / 2;
               }
           } else {
               while (cycleMath > cycle) {
                   cycle = cycle * 2;
               }
           }
           // Generate X labels
           double val = mStartPoint;
           int resultIndex = 0;
           while (val > min) {
               val -= cycle; // Step back to find the first label in range
           }
           int i = 0;

           while (val < max && i < count) {
               result[resultIndex++] = (int) val;
               val += cycle;
               i++;
           }
       }
       assert(result != null && result.length > 0);

        return result;
    }

    public static void main(String[] args){
        int max = Verifier.nondetInt();
        int min = Verifier.nondetInt();
        int count = Verifier.nondetInt();
        if(max >=0 && min >= 0 && count > 0){
            if(max >= min ) {
                getXLabels(min,max,count);
            }
        }
    }
}
