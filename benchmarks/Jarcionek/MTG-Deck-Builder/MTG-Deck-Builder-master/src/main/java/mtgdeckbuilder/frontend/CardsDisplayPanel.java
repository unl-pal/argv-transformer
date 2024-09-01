/** filtered and transformed by PAClab */
package mtgdeckbuilder.frontend;

import org.sosy_lab.sv_benchmarks.Verifier;

//TODO Jarek: make some cool effects here!
public class CardsDisplayPanel {

    /** PACLab: suitable */
	 private void display() {
        int MARGIN = Verifier.nondetInt();
		int selectedCard = Verifier.nondetInt();
		for (int i = selectedCard - MARGIN; i <= selectedCard + MARGIN; i++) {
            if (i < 0 || i >= Verifier.nondetInt()) {
                continue;
            }

            int labelWidth = (int) Verifier.nondetInt();
            int labelHeight = (int) Verifier.nondetInt();
            int panelWidth = (int) Verifier.nondetInt();

            int xLength = panelWidth - labelWidth;

            int diff = Verifier.nondetInt() ;
        }

        if (rand.nextBoolean()) {
        }
        for (int i = selectedCard + 1; i <= selectedCard + MARGIN; i++) {
            if (i >= 0 && i < Verifier.nondetInt()) {
            }
        }
        for (int i = selectedCard - 1; i >= selectedCard - MARGIN; i--) {
            if (i >= 0 && i < Verifier.nondetInt()) {
            }
        }
    }

}
