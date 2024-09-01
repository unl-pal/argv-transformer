/**
 * 
 */
package org.devel.jfxcontrols.scene.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;

import com.sun.javafx.css.converters.SizeConverter;

/**
 * Example 4 the definition of stylable properties using the JavafX 8 CSS API.
 * 
 * @author stefan.illgen
 * 
 */
public class RotatableCheckBox extends CheckBox {

	private DoubleProperty rotate1;

	private static final String DEFAULT_STYLE_CLASS = "rotatable-check-box";

	private static final List<CssMetaData<? extends Styleable, ?>> CSS_META_DATA;
	// static initiation
	static {
		final List<CssMetaData<? extends Styleable, ?>> metaData = new ArrayList<CssMetaData<? extends Styleable, ?>>(
				Labeled.getClassCssMetaData());
		Collections.addAll(metaData, StyleableProperties.ROTATE);
		CSS_META_DATA = Collections.unmodifiableList(metaData);
	}

	private static class StyleableProperties {

		// ROTATE
		private static final CssMetaData<RotatableCheckBox, Number> ROTATE = new CssMetaData<RotatableCheckBox, Number>(
				"-dev-rotate1", SizeConverter.getInstance(), 0.0) {

			@Override
			public boolean isSettable(RotatableCheckBox node) {
				return node.canSetRotate1();
			}

			@SuppressWarnings("unchecked")
			@Override
			public StyleableProperty<Number> getStyleableProperty(
					RotatableCheckBox node) {
				return (StyleableProperty<Number>) node.rotate1Property();
			}
		};

	}

}
