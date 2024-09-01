package org.devel.jfxcontrols.scene.control;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.stage.FileChooser.ExtensionFilter;
import org.devel.jfxcontrols.scene.control.skin.FileSelectionDropAreaSkin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.beans.binding.Bindings.createStringBinding;

/**
 * A control for dropping files for selection providing an alternative way to trigger the selection using a button.
 */
public class FileSelectionDropArea extends Control {

  public static final String INITIAL_DIRECTORY = "user.home";
  public static final String DEFAULT_SEARCH_BUTTON_TEXT = "Search";
  public static final String DEFAULT_FILE_DIALOG_TITLE = "Open";
  private static final String DEFAULT_STYLE_CLASS = "file-selection-drop-area";
  private static final PseudoClass PSEUDO_CLASS_HIGHLIGHTED = PseudoClass.getPseudoClass("highlighted");

  private final BooleanProperty directorySelection = new SimpleBooleanProperty();

  private final ObjectProperty<Path> selectedFile = new SimpleObjectProperty<>();
  private final ReadOnlyObjectWrapper<Path> cachedDirectory = new ReadOnlyObjectWrapper<>();
  private final ReadOnlyStringWrapper selectedFileName = new ReadOnlyStringWrapper();

  private final StringProperty buttonText = new SimpleStringProperty(DEFAULT_SEARCH_BUTTON_TEXT);
  private final StringProperty dialogTitle = new SimpleStringProperty(DEFAULT_FILE_DIALOG_TITLE);

  private final BooleanProperty fileError = new SimpleBooleanProperty();

  private final ObservableList<ExtensionFilter> extensionFilters = FXCollections.<ExtensionFilter>observableArrayList();

  private final List<Predicate<Path>> validators = new LinkedList<>();
  /**
   * Indicates whether this drop area is highlighted. This can be manipulated programmatically.
   */
  private BooleanProperty highlighted;
  private final StringProperty fileErrorTooltipText = new SimpleStringProperty("");
}
