/** filtered and transformed by PAClab */

import org.sosy_lab.sv_benchmarks.Verifier;

//TODO Jarek: make some cool effects here!
public class Main {

    /** PACLab: suitable */
	 public static void display() {
        int margin = Verifier.nondetInt();
		int selectedCard = Verifier.nondetInt();
		for (int i = selectedCard - margin; i <= selectedCard + margin; i++) {
            if (i < 0 || i >= Verifier.nondetInt()) {
                continue;
            }
            int labelWidth = (int) Verifier.nondetInt();
            int labelHeight = (int) Verifier.nondetInt();
            int panelWidth = (int) Verifier.nondetInt();

            if(labelWidth < 0 || labelHeight < 0 || panelWidth < 0 ){
                return;
            }
            int xLength = panelWidth - labelWidth;
            assert (xLength <= panelWidth || xLength <= labelWidth);
        }
    }
    public static void main(String[] args) {
       try{
           display();
       } catch (Exception e){
           System.out.println(e);
       }
    }

}
