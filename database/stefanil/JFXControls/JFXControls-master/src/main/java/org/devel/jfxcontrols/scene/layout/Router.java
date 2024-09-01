package org.devel.jfxcontrols.scene.layout;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import org.devel.jfxcontrols.scene.control.RouteMapView;
import org.devel.jfxcontrols.scene.control.skin.RouterSkin;

/**
 * Aggregation Example using just one {@link ObjectProperty} type.
 * 
 * + full properties of aggregated controls, without writing much of code
 * 
 * - encapsulation: router makes a lot of assumptions on its contents but does
 * not expose all of its controls and layouts, thus if someone wants 2 adapt
 * this control in another context, he has to check implementation details
 * 
 * - also FXML children will also be not possible
 * 
 * 
 * @author stefan.illgen
 * 
 */
public class Router extends Control {

	private static final String DEFAULT_STYLE_CLASS = "router";

	private ObjectProperty<RouteMapView> routeMapView;
	private StringProperty startLabel;
	private StringProperty startText;
	private StringProperty finishLabel;
	private StringProperty finishText;
	private StringProperty buttonText;

}
