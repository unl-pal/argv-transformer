/** filtered and transformed by PAClab */
package mtgdeckbuilder.frontend;

import gov.nasa.jpf.symbc.Debug;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

//TODO Jarek: make some cool effects here!
public class CardsDisplayPanel {

    /** PACLab: suitable */
	 private void display() {
        int MARGIN = Debug.makeSymbolicInteger("x1");
		int selectedCard = Debug.makeSymbolicInteger("x0");
		for (int i = selectedCard - MARGIN; i <= selectedCard + MARGIN; i++) {
            if (i < 0 || i >= Debug.makeSymbolicInteger("x2")) {
                continue;
            }

            int labelWidth = (int) Debug.makeSymbolicInteger("x3");
            int labelHeight = (int) Debug.makeSymbolicInteger("x4");
            int panelWidth = (int) Debug.makeSymbolicInteger("x5");

            int xLength = panelWidth - labelWidth;

            int diff = Debug.makeSymbolicInteger("x6") ;
        }

        if (Debug.makeSymbolicBoolean("x7")) {
        }
        for (int i = selectedCard + 1; i <= selectedCard + MARGIN; i++) {
            if (i >= 0 && i < Debug.makeSymbolicInteger("x8")) {
            }
        }
        for (int i = selectedCard - 1; i >= selectedCard - MARGIN; i--) {
            if (i >= 0 && i < Debug.makeSymbolicInteger("x9")) {
            }
        }
    }

}
