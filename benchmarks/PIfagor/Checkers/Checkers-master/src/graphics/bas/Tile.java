/** filtered and transformed by PAClab */
package graphics.bas;

import org.sosy_lab.sv_benchmarks.Verifier;

public class Tile {

	/** PACLab: suitable */
	 private void showPosibleMove() {
		int J = Verifier.nondetInt();
		int I = Verifier.nondetInt();
		int ai = I - 1;
		int aj = J + 1;

		int bi = I - 1;
		int bj = J - 1;

		if (aj < Verifier.nondetInt() && ai > 0 && Verifier.nondetInt() == -1) {
		}

		if (bj < Verifier.nondetInt() && bi > 0 && Verifier.nondetInt() == -1) {
		}

		if (ai < Verifier.nondetInt() && aj > 0 && Verifier.nondetInt() == 0) {
		}
		if (bi < Verifier.nondetInt() && bj > 0 && Verifier.nondetInt() == 0) {
		}

	}

}
