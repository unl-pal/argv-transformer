/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import org.devel.jfxcontrols.scene.control.RouteMapView;
import org.devel.jfxcontrols.scene.layout.Router;

/**
 * @author stefan.illgen
 *
 */
public class RouterSkin extends SkinBase<Router> {
	
	private static final String DEFAULT_START_POSITION = "51.02681 13.70878";
	private static final String DEFAULT_FINISH_POSITION = "51.05041 13.73726";

	private AnchorPane searchRoutePane;
	private RouteMapView routeMapView;
	private Label finishLbl;
	private TextField finishTf;
	private Button searchBtn;
	private Label startLbl;
	private TextField startTf;

}
