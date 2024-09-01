/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import java.io.File;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import org.devel.jfxcontrols.concurrent.ImageWriter;
import org.devel.jfxcontrols.concurrent.ImageLoader;
import org.devel.jfxcontrols.scene.control.ImageCropper;
import org.devel.jfxcontrols.scene.control.ImageCropperScrollPane;
import org.devel.jfxcontrols.scene.image.SourceImageView;
import org.devel.jfxcontrols.scene.shape.CropperRectangle;

/**
 * @author stefan.illgen
 * 
 */
public class ImageCropperSkin extends SkinBase<ImageCropper> {

	// TODO stefan - import from i18n
	private static final String SOURCE_CHOOSE_LABEL = "Choose Source File";
	private static final String TARGET_CHOOSE_LABEL = "Choose Target File";
	private static final String SOURCE_LABEL = "Source";
	private static final String TARGET_LABEL = "Target";
	private static final String TARGET_BUTTON_TEXT = "Save Image";
	private static final String SOURCE_BUTTON_TEXT = "Load Image";

	// fixed width constants
	private static final double SOURCE_IMAGE_WIDTH = 200.0;
	private static final double SOURCE_IMAGE_HEIGHT = 266.0;
	private static final double SOURCE_SCROLL_PANE_MIN_WIDTH = 103.0;
	private static final double SOURCE_SCROLL_PANE_MIN_HEIGHT = 137.0;
	private static final double TARGET_IMAGE_WIDTH = 100.0;
	private static final double TARGET_IMAGE_HEIGHT = 133.0;
	private static final double TARGET_HBOX_WIDTH = 122.0;
	private static final double TARGET_HBOX_HEIGHT = 155.0;

	private static final double GRID_PADDING = 10.0;
	private static final double GRID_GAP = 10.0;

	private static final Color CROPPER_COLOR = new Color(1.0, 1.0, 1.0, 0.5);
	private static final Color CROPPER_STROKE_COLOR = new Color(
			0.8666666746139526, 0.8666666746139526, 0.8666666746139526, 1.0);
	private static final double CROPPER_WIDTH = 100.0;
	private static final double CROPPER_HEIGHT = 133.33333;

	private GridPane imageCropperGridPane;
	private Button loadImageButton;
	private Button saveImageButton;
	private Label sourceLabel;
	private Label targetLabel;
	private ImageCropperScrollPane imageCropperScrollPane;
	private StackPane imageCropperStackPane;
	private CropperRectangle cropperRectangle;
	private SourceImageView sourceImageView;
	private ImageView targetImageView;

}
