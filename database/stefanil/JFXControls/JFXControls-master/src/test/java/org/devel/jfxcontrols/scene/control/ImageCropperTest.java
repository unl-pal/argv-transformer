/**
 * 
 */
package org.devel.jfxcontrols.scene.control;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.loadui.testfx.Assertions.verifyThat;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;

import org.apache.commons.io.FileUtils;
import org.devel.jfxcontrols.concurrent.ImageLoader;
import org.devel.jfxcontrols.concurrent.ImageWriter;
import org.devel.jfxcontrols.conf.Properties;
import org.devel.jfxcontrols.scene.image.SourceImageView;
import org.devel.jfxcontrols.scene.shape.CropperRectangle;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.TestUtils;

/**
 * 
 * @author stefan.illgen
 * 
 */
public class ImageCropperTest extends GuiTest {

	private ImageCropper root;
	private SourceImageView sourceImageView;
	private CropperRectangle cropperRectangle;
	private boolean saveFinished;

}
