/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin.animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.control.IndexedCell;
import javafx.util.Duration;

/**
 * @author stefan.illgen
 *
 */
public class EntireRowAdjuster<M, I extends IndexedCell<M>> extends Transition {

	private static Duration DURATION_SCROLL_ADJUST_AFTER_DRAG = Duration.millis(300);
	private static Interpolator STRONG_EASE_OUT = Interpolator.SPLINE(0.5, 0.9, 0.9, 0.95);
	private double totalYAdjustForAnimation;
	private double alreadyYAdjustForAnimation;
	private EntireRowAdjustReceiver receiver;

}
