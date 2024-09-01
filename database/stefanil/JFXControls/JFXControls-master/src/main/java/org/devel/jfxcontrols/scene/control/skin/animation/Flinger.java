/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin.animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.IndexedCell;
import javafx.util.Duration;

/**
 * @author stefan.illgen
 *
 */
public class Flinger<M, I extends IndexedCell<M>> extends Transition {

	private final static double DECCELERATION = 1000;

	// private ExtensibleFlow<M, I> extensibleFlow;

	private double maxAngle;

	private double velocityAdjusted;

	private double accelerationAdjusted;

	private double velocity;

	private double distanceFactor;

	private EventHandler<ActionEvent> onFinishHandler;

	private MaxDistanceAdjuster maxDistanceAdjuster;

	private FlingReceiver receiver;

	private double angleAlreadyAdjusted;

}
