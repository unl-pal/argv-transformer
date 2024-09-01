/**
 * 
 */
package org.devel.jfxcontrols.scene.control;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.devel.jfxcontrols.concurrent.RouteComputer;
import org.devel.jfxcontrols.concurrent.WebEngineLoader;
import org.devel.jfxcontrols.scene.control.skin.RouteMapViewSkin;

/**
 * @author stefan.illgen
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RouteMapView extends Control {

	private static final String DEFAULT_STYLE_CLASS = "route-map-view";
	// batch delay in seconds
	private static final int BATCH_DELAY = 2;

	private static final String GOOGLEMAPS_HTML = "googleMapsJSAPI.html";

	private WebView routeWebView;
	private WebEngine webEngine;
	private ScheduledThreadPoolExecutor threadPool;
	private ScheduledFuture<?> activeTask;

	private StringProperty startPosition;
	private StringProperty finishPosition;

	private Map<ObservableValue, ChangeListener> cls = new HashMap<ObservableValue, ChangeListener>();

}
