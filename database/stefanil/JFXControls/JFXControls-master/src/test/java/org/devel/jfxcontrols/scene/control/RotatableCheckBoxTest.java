package org.devel.jfxcontrols.scene.control;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.loadui.testfx.Assertions.verifyThat;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import org.devel.jfxcontrols.conf.Properties;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class RotatableCheckBoxTest extends GuiTest {

	private AnchorPane root;
	
	@FXML
	private RotatableCheckBox myrotcheckbox;

}
