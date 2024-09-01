/**
 * 
 */
package org.devel.jfxcontrols.resource;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author stefan.illgen
 * 
 */
public class ImageRegistry {

	private static ImageRegistry INSTANCE;

	private Map<Integer, Image> allImages = new HashMap<Integer, Image>();

}
