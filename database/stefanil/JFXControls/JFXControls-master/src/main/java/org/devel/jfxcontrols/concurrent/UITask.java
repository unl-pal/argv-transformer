/**
 * 
 */
package org.devel.jfxcontrols.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * This task can be used as base for running {@link Task}s in a synchronized
 * way. For this purpose it provides {@link #runSync(Runnable)} 2 run a
 * {@link Runnable} inside the UI thread and subsequently wait and
 * {@link #rideOn()}
 * 
 * @author stefan.illgen
 * 
 */
public abstract class UITask<V> extends Task<V> {

	private static final long DEFAULT_TIME_OUT = 20;
	private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	
	private CountDownLatch latch;
	private long timeout;
	private TimeUnit timeUnit;

}
