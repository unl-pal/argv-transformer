/**
 * 
 */
package org.devel.jfxcontrols.concurrent;

import javafx.scene.web.WebEngine;

/**
 * @author stefan.illgen
 * 
 */
public class RouteComputer extends UITask<Boolean> {

	private static final int WORK_DONE_EXECUTING = 0x32;
	private static final int WORK_DONE_EXECUTE = 0x64;
	private static final int WORK_TOTAL = 100;

	private WebEngine webEngine;
	private String startPosition;
	private String finishPosition;

}
