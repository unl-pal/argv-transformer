/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.command;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.IndexedCell;
import javafx.util.Duration;

import org.devel.jfxcontrols.scene.control.skin.animation.MaxDistanceAdjuster;

/**
 * @author stefan.illgen
 *
 */
public class Fling<T, A extends IndexedCell<T>> extends Transition
	implements
		Command<Fling.Action, Flingable> {

	private final static double DECCELERATION = 1000;

	public enum Action implements Command.Action<Fling.Action> {
		PRESS, DRAG, FLING;

		private double y;
		private boolean animate;
		public double velocity;

		private Flingable flingable;
	private double distanceFactor;
	private MaxDistanceAdjuster maxDistanceAdjuster;
	private EventHandler<ActionEvent> onFinishHandler;
	private int angleAlreadyAdjusted;
	private double velocityAdjusted;
	private double accelerationAdjusted;
	private double maxAngle;
	private RowAdjust<T, A> rowAdjust;

}
