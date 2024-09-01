package org.devel.jfxcontrols.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.devel.jfxcontrols.resource.ImageRegistry;
import org.devel.jfxcontrols.resource.Images;
import org.devel.jfxcontrols.scene.control.Aggregator;
import org.devel.jfxcontrols.scene.control.CircleCheckBox;
import org.devel.jfxcontrols.scene.control.CircleCheckBoxSkin;
import org.devel.jfxcontrols.scene.control.FileSelectionDropArea;
import org.devel.jfxcontrols.scene.control.FilterableTableView;
import org.devel.jfxcontrols.scene.control.ImageCropper;
import org.devel.jfxcontrols.scene.control.ReflectableTreeItem;
import org.devel.jfxcontrols.scene.control.RotatableCheckBox;
import org.devel.jfxcontrols.scene.control.skin.RotatableCheckBoxSkin;
import org.devel.jfxcontrols.scene.control.svg.SvgIconButton;
import org.devel.jfxcontrols.scene.control.svg.SvgIconRadioButton;
import org.devel.jfxcontrols.scene.control.svg.SvgIconToggleButton;
import org.devel.jfxcontrols.scene.layout.Router;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author stefan.illgen
 */
public class JFXShowCase extends AnchorPane implements Initializable {

  @FXML
  private ImageView logoImageView;

  @FXML
  private Text logoText;

  @FXML
  private Accordion detailsAccordeon;

  @FXML
  private TitledPane exampleTitledPane;

  @FXML
  private TreeView<String> masterTreeView;

  @FXML
  private TitledPane scTitledPane;

  @FXML
  private AnchorPane exampleContainer;

  @FXML
  private TextArea scTextArea;
}
