package org.devel.jfxcontrols.scene.control.svg;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableStringProperty;
import javafx.css.StyleConverter;
import javafx.css.StyleableProperty;
import javafx.css.StyleableStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Labeled;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Styles a {@link Labeled} with an svg icon. The icon can be specified via css option -sx-svgpath.
 * <p>
 * A new Class that should be styled with this icon has to construct an object of this type and add the result of {@link
 * SvgIconStyle#cssMetaData} to its {@link javafx.scene.control.Control#getControlCssMetaData()}.
 */
public class SvgIconStyle {

  private static final String SX_SVG_PATH = "-sx-svgpath";

  private final StyleableStringProperty svgIconPath;
  private final CssMetaData<Labeled, String> cssMetaData;
  private final Consumer<Node> propConsumer;
}
