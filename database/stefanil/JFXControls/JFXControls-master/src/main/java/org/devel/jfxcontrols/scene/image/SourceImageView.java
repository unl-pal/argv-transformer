package org.devel.jfxcontrols.scene.image;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;

import org.devel.jfxcontrols.scene.shape.CropperRectangle;

/**
 * This is no {@link Control}, thus layout calculations take place in here. 
 * 
 * @author stefan.illgen
 * 
 */
public class SourceImageView extends ImageView {

	/*
	 * Property 4 handling custom rectangles.
	 */
	private ObjectProperty<CropperRectangle> _cropperRectangle;

}
