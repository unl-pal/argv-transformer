package com.aplicant.test.client.activity.welcome;

import com.aplicant.test.client.activity.CommonActivity;
import com.aplicant.test.client.event.welcome.GotoBookEvent;
import com.aplicant.test.client.event.welcome.GotoBookHandler;
import com.aplicant.test.client.factory.ClientFactory;
import com.aplicant.test.client.place.phonebook.Phonebook;
import com.aplicant.test.client.view.welcome.WelcomeView;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class WelcomeActivity extends CommonActivity {
	
	private ClientFactory clientFactory;
}
