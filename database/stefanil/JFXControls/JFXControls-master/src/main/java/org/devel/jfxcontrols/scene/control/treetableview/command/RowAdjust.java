/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.command;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.control.IndexedCell;
import javafx.util.Duration;

/**
 * @author stefan.illgen
 *
 */
public class RowAdjust<T, I extends IndexedCell<T>> extends Transition
	implements
		Command<RowAdjust.Action, Adjustable<T, I>> {

	private static Duration DURATION_SCROLL_ADJUST_AFTER_DRAG = Duration.millis(300);
	private static Interpolator STRONG_EASE_OUT = Interpolator.SPLINE(0.5, 0.9, 0.9, 0.95);

	private double totalYAdjustForAnimation;
	private double alreadyYAdjustForAnimation;
	private double currentMouseY;
	// private VelocityTracker velocityTracker;
	private boolean dragging;
	private Adjustable<T, I> adjustable;
	private double intialFlowPosition;
	private int selectedIndex = -1;

	public enum Action implements Command.Action<RowAdjust.Action> {
		INIT,
		DRAG,
		ADJUST_AFTER_DRAG,
		CONSUME,
		ADJUST_ROW_INDEX,
		INIT_LAYOUT_CHANGE,
		ADJUST_DELTA;

		private double y = 0.0d;;
		private boolean animate;
		private double delta;
		private int rowIndex;
		private ReadOnlyBooleanProperty needsLayout;
		private double length;

		}

}
