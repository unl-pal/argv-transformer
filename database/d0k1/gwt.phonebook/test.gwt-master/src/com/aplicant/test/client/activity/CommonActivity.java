package com.aplicant.test.client.activity;

import java.util.ArrayList;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.web.bindery.event.shared.HandlerRegistration;

public abstract class CommonActivity extends AbstractActivity {
	
	private ArrayList<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
}
