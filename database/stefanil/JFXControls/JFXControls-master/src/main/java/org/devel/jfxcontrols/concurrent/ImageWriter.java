/**
 * 
 */
package org.devel.jfxcontrols.concurrent;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

/**
 * @author stefan.illgen
 * 
 */
public class ImageWriter extends Task<Boolean> {

	private static final String SAVE_IMAGE_SUCCESS_MSG = " erfolgreich gespeichert.";
	private static final String SAVE_IMAGE_ERROR_MSG = "Fehler beim Speichern von ";
	private File imageFile;
	private Image image;
	private int x;
	private int y;
	private int width;
	private int height;

}
