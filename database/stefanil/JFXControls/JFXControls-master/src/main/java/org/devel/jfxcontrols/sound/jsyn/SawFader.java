package org.devel.jfxcontrols.sound.jsyn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;

public class SawFader extends Application {

    private static final URL URL = SawFader.class.getResource("./SawFader.fxml");

    private static final double MINIMUM_FREQUENCY = 200;
    private static final double BANDWIDTH = 500.0;
    private static final double KNOB_RANGE_IN_DEGREE = 300;
    private static final double DRAG_AREA_HEIGHT = 150;

    private Synthesizer synth;
    private UnitOscillator osc;
    private LinearRamp lag;
    private LineOut lineOut;

    public class SawFaderController implements Initializable {

        @FXML
        private Pane rotatePane;

        @FXML
        private Circle rotateCircle;
        private double lastY = Double.NaN;

        private Rotate rotate;
    }
}
