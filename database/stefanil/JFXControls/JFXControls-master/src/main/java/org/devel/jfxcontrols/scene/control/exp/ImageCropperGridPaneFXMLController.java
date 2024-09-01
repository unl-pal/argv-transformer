/**
 * 
 */
package org.devel.jfxcontrols.scene.control.exp;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import org.devel.jfxcontrols.concurrent.ImageWriter;
import org.devel.jfxcontrols.concurrent.ImageLoader;
import org.devel.jfxcontrols.scene.control.ImageCropperScrollPane;
import org.devel.jfxcontrols.scene.image.SourceImageView;
import org.devel.jfxcontrols.scene.shape.CropperRectangle;

/**
 * 
 * 
 * @author stefan.illgen
 * 
 */
public class ImageCropperGridPaneFXMLController implements Initializable {

	private static final String TXT_choose_source_label = "Quelle";
	private static final String TXT_choose_target_label = "Ziel";

	@FXML
	private GridPane imageCropperView;

	// the image cropper pane used for loading FXML
	// private ImageCropperGridPane imageCropperPane;

	@FXML
	private StackPane test;

	@FXML
	private ImageCropperScrollPane imageCropperScrollPane;

	@FXML
	private SourceImageView sourceImageView;

	@FXML
	private CropperRectangle cropperRectangle;

	@FXML
	private ImageView targetImageView;

	@FXML
	private Button saveImageButton;

}
