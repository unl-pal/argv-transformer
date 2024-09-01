package org.devel.jfxcontrols.scene.control.compass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleConverter;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * @author stefan.illgen
 */
public class TappasCompass extends Control {

    private static final String DEFAULT_STYLE_CLASS = "tappas-compass";

    private static final double DFEAULT_SCALE = 8.0d;
    private static final double DEFAULT_ORIENTATION = 0.0d;
    private static final double DEFAULT_TILT = 0.0d;
    private static final Pos DEFAULT_ALIGNMENT = Pos.CENTER;

    private ObjectProperty<Pos> alignment;

    private DoubleProperty orientation;

    private DoubleProperty tilt;

    private DoubleProperty scale;

    /**
     * Super-lazy instantiation pattern from Bill Pugh.
     * 
     * @treatAsPrivate implementation detail
     */
    @SuppressWarnings("unchecked")
    private static class StyleableProperties {

        protected static final CssMetaData<TappasCompass, Number> ORIENTATION =
            new CssMetaData<TappasCompass, Number>("-tappas-compass-orientation",
                                                   StyleConverter.getSizeConverter(),
                                                   0.0d) {

                @Override
                public boolean isSettable(TappasCompass node) {
                    return node.orientation == null || !node.orientation.isBound();
                }

                @Override
                public StyleableProperty<Number> getStyleableProperty(TappasCompass node) {
                    return (StyleableProperty<Number>) node.orientationProperty();
                }
            };

        protected static final CssMetaData<TappasCompass, Number> TILT =
            new CssMetaData<TappasCompass, Number>("-tappas-compass-tilt",
                                                   StyleConverter.getSizeConverter(),
                                                   DEFAULT_TILT) {

                @Override
                public boolean isSettable(TappasCompass node) {
                    return node.tilt == null || !node.tilt.isBound();
                }

                @Override
                public StyleableProperty<Number> getStyleableProperty(TappasCompass node) {
                    return (StyleableProperty<Number>) node.tiltProperty();
                }
            };

        protected static final CssMetaData<TappasCompass, Number> SCALE =
            new CssMetaData<TappasCompass, Number>("-tappas-compass-scale",
                                                   StyleConverter.getSizeConverter(),
                                                   DFEAULT_SCALE) {

                @Override
                public boolean isSettable(TappasCompass node) {
                    return node.scale == null || !node.scale.isBound();
                }

                @Override
                public StyleableProperty<Number> getStyleableProperty(TappasCompass node) {
                    return (StyleableProperty<Number>) node.scaleProperty();
                }
            };

        private static final CssMetaData<TappasCompass, Pos> ALIGNMENT =
            new CssMetaData<TappasCompass, Pos>("-tappas-compass-alignment",
                                                (StyleConverter<?, Pos>) StyleConverter.<Pos> getEnumConverter(Pos.class),
                                                DEFAULT_ALIGNMENT) {

                @Override
                public boolean isSettable(TappasCompass node) {
                    return node.alignment == null || !node.alignment.isBound();
                }

                @Override
                public StyleableProperty<Pos> getStyleableProperty(TappasCompass node) {
                    return (StyleableProperty<Pos>) node.alignmentProperty();
                }
            };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<CssMetaData<? extends Styleable, ?>>(Control.getClassCssMetaData());
            styleables.add(ALIGNMENT);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

}
