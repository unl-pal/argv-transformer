package com.aplicant.test.client.factory;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;

import com.aplicant.test.client.view.card.CardView;
import com.aplicant.test.client.view.card.CardViewImpl;
import com.aplicant.test.client.view.phonebook.PhonebookView;
import com.aplicant.test.client.view.phonebook.PhonebookViewImpl;
import com.aplicant.test.client.view.welcome.WelcomeView;
import com.aplicant.test.client.view.welcome.WelcomeViewImpl;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {

	private final WelcomeView welcomeView = new WelcomeViewImpl();
	
	private final PhonebookView phonebookView = new PhonebookViewImpl();
	private final CardView cardView = new CardViewImpl();
	
	private final DispatchAsync dispatch = new StandardDispatchAsync(new DefaultExceptionHandler());
	
	private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);
	
}
