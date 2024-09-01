/**
 * 
 */
package org.devel.jfxcontrols.concurrent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

import org.devel.jfxcontrols.conf.AppMode;

/**
 * @author stefan.illgen
 * 
 */
@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class WebEngineLoader extends UITask<Boolean> {

	private static final long TOTAL_WORK = 100;
	private static final long WORK_DONE_LOAD = 100;
	private static final long WORK_DONE_PRE_LOAD = 33;

	private WebEngine webEngine;
	private URL url;
	private Worker<Void> loadWorker;
	private Map<ChangeListener, ObservableValue> cls = new HashMap<ChangeListener, ObservableValue>();

}
