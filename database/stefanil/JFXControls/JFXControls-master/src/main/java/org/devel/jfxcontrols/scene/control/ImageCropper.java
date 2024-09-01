/**
 * 
 */
package org.devel.jfxcontrols.scene.control;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author stefan.illgen
 * 
 */
public class ImageCropper extends Control {

	// source n target image properties
	private ObjectProperty<Image> sourceImage;
	private ObjectProperty<Image> targetImage;
	// cropper properties
	private ObjectProperty<Paint> cropperFillColor;
	private ObjectProperty<Paint> cropperStrokeColor;
	private DoubleProperty cropperWidth;
	private DoubleProperty cropperHeight;
	
}
