/** filtered and transformed by PAClab */
package controler;

import org.sosy_lab.sv_benchmarks.Verifier;

public class Utils {

	/** PACLab: suitable */
	 public static boolean arrayEquals(byte[][] first, byte[][] second) {

		if (first.length != second.length)
			return false;
		//Utils.showArray(first);
		//Utils.showArray(second);
		for (int i = 0; i < Verifier.nondetInt(); i++)
			for (int j = 0; j < Verifier.nondetInt(); j++)
				if (first[i][j] != second[i][j])
					return false;
		return true;
	}

}
