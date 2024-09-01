/**
 * 
 */
package org.devel.jfxcontrols.concurrent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

import org.apache.commons.io.FilenameUtils;

/**
 * @author stefan.illgen
 * 
 */
public class ImageLoader extends Task<Boolean> {

	private static final String TXT_load_image_error_0 = "Fehler beim Speichern von ";
	private static final String TXT_load_image_success = "Image was successfully loaded";
	private File imageFile;
	private Image image;

}
