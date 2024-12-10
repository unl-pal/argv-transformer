/** filtered and transformed by PAClab */


import org.sosy_lab.sv_benchmarks.Verifier;

public class Main {

	/**
	 * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
	 *
	 * @param x the number of pixels to scroll by on the X axis
	 * @param y the number of pixels to scroll by on the Y axis
	 * @param velocity the velocity associated with a fling, if applicable. (0 otherwise)
	 */
	/** PACLab: suitable */
	public static void smoothScrollTo(int x, int y, int velocity) {
		int MAX_SETTLE_DURATION = Verifier.nondetInt();
		boolean mScrolling = Verifier.nondetBoolean();
		boolean mClosedListener = Verifier.nondetBoolean();
		boolean mOpenedListener = Verifier.nondetBoolean();
		if (Verifier.nondetInt() == 0) {
			return;
		}
		int sx = Verifier.nondetInt();
		int sy = Verifier.nondetInt();
		int dx = x - sx;
		int dy = y - sy;
		if (dx == 0 && dy == 0) {
			return;
		}
		mScrolling = true;

		final int width = Verifier.nondetInt();
		final int halfWidth = width / 2;
		final float distanceRatio = Verifier.nondetFloat();
		final float distance = halfWidth + halfWidth *
				Verifier.nondetInt();
		int duration = 0;
		velocity = Verifier.nondetInt();
		if (velocity > 0) {
			duration = 4 * Verifier.nondetInt();
		} else {
			final float pageDelta = (float) Verifier.nondetFloat() / width;
			duration = (int) ((pageDelta + 1) * 100);

		}
		assert(dx != 0 || dy != 0);



	}
	public static void main(String[] args){
		int x = Verifier.nondetInt();
		int y = Verifier.nondetInt();
		int velocity = Verifier.nondetInt();
		try{
			smoothScrollTo(x, y, velocity);
		} catch (ArithmeticException e){}
	}
}




