/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.IndexedCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import org.devel.jfxcontrols.scene.control.skin.animation.EntireRowAdjuster;
import org.devel.jfxcontrols.scene.control.skin.animation.VelocityTracker;

import com.sun.javafx.scene.control.skin.VirtualFlow;

/**
 * @author stefan.illgen
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowAdjuster<M, I extends IndexedCell<M>> extends
		FlowExtension<M, I> {

	private EntireRowAdjuster<M, I> adjuster;
	private VelocityTracker velocityTracker;
	private double startY;
	private double currentY;
	private boolean isAdjusting;

}
