package org.devel.jfxcontrols.resource;

/**
 * 
 * @author stefan.illgen
 * 
 */
public enum Images {

	// ### Image Configuration ###

	GORL_ALICED_100("goerl_aliced_100.jpg"), 
	GORL_ALICED_50("goerl_aliced_50.jpg"), 
	GORL_ALICED_500("goerl_aliced_500.jpg"), 
	LOGO_16("logo16.png"),
	LOGO_128("logo128.png"), 
	LOGO_32("logo32.png"),
	LOGO_64("logo64.png");

	/**
	 * The base directory which contains all images (eventually using sub
	 * folders).
	 */
	public static final String IMAGE_BASE_DIR = "/org/devel/jfxcontrols/sample/img";

	// ### public API ###

	public String getFullPath() {
		return fullpath;
	}

	// ### private API ###

	private String fullpath;

	private Images(String path) {
		fullpath = IMAGE_BASE_DIR + "/" + path;
	}

}
