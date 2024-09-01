/**
 * 
 */
package org.devel.jfxcontrols.scene.control.compass;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;

/**
 * @author stefan.illgen
 */
public class TappasCompassSkin extends SkinBase<TappasCompass> {

    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(TappasCompassSkin.class);
    private static final URL FXML_RESOURCE_PATH = TappasCompass.class.getResource(TappasCompass.class.getSimpleName()
        + ".fxml");

    private SubScene compassScene;

    /**
     * @author stefan.illgen
     */
    public class TappasCompassController implements Initializable {

        @FXML
        private StackPane compassRoot;

        @FXML
        private Group north;

        @FXML
        private PhongMaterial phongMaterial;

        @FXML
        private Group needle;
    }
}
