package com.aplicant.test.client.activity;

import com.aplicant.test.client.activity.card.create.CreateActivity;
import com.aplicant.test.client.activity.card.details.DetailsActivity;
import com.aplicant.test.client.activity.phonebook.PhonebookActivity;
import com.aplicant.test.client.activity.welcome.WelcomeActivity;
import com.aplicant.test.client.factory.ClientFactory;
import com.aplicant.test.client.place.create.Create;
import com.aplicant.test.client.place.details.Details;
import com.aplicant.test.client.place.phonebook.Phonebook;
import com.aplicant.test.client.place.welcome.Welcome;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class ApplicationActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;
}
