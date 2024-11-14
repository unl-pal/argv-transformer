/** filtered and transformed by PAClab */
package com.jeremyfeinstein.slidingmenu.lib;

import org.sosy_lab.sv_benchmarks.Verifier;

public class CustomViewAbove {

	/**
	 * Simple implementation of the {@link OnPageChangeListener} interface with stub
	 * implementations of each method. Extend this if you do not intend to override
	 * every method of {@link OnPageChangeListener}.
	 */
	public static class SimpleOnPageChangeListener {

	}

	/**
	 * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
	 *
	 * @param x the number of pixels to scroll by on the X axis
	 * @param y the number of pixels to scroll by on the Y axis
	 * @param velocity the velocity associated with a fling, if applicable. (0 otherwise)
	 */
	/** PACLab: suitable */
	 void smoothScrollTo(int x, int y, int velocity) {
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
			if (Verifier.nondetBoolean()) {
				if (mOpenedListener != null) {
				}
			} else {
				if (mClosedListener != null) {
				}
			}
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
			duration = MAX_SETTLE_DURATION;
		}
		duration = Verifier.nondetInt();
	}

}
