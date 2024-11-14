package controler;

import constant.CT;

public class Utils {

	public static boolean arrayEquals(byte[][] first, byte[][] second) {

		if (first.length != second.length)
			return false;
		//Utils.showArray(first);
		//Utils.showArray(second);
		for (int i = 0; i < first.length; i++)
			for (int j = 0; j < first.length; j++)
				if (first[i][j] != second[i][j])
					return false;
		return true;
	}

}
