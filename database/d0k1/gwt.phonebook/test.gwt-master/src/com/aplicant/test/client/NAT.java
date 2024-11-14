package com.aplicant.test.client;

import com.aplicant.test.client.activity.ApplicationActivityMapper;
import com.aplicant.test.client.factory.ClientFactory;
import com.aplicant.test.client.place.ApplicationHistoryMapper;
import com.aplicant.test.client.place.welcome.Welcome;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NAT implements EntryPoint {
	private SimplePanel appWidget = new SimplePanel();
	private Welcome defaultPlace = new Welcome();
}
