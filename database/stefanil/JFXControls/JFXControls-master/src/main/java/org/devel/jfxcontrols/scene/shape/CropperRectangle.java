/**
 * 
 */
package org.devel.jfxcontrols.scene.shape;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * This {@link Rectangle} must know source image to calculate its movement
 * concerning minimum and maximum.
 * 
 * @author stefan.illgen
 * 
 */
public class CropperRectangle extends Rectangle {

	// reference points of mouse pressed location
	private DoubleProperty refX;
	private DoubleProperty refY;
	// maximum points for moving the rectangle (this is at least the size of the
	// rectangle > so this means the right downer edge)
	private DoubleProperty maxX;
	private DoubleProperty maxY;

}
