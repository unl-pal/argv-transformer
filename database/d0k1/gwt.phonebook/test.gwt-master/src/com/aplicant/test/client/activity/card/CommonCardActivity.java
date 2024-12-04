package com.aplicant.test.client.activity.card;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.aplicant.test.client.activity.CommonActivity;
import com.aplicant.test.client.event.card.delete.DeleteContactEvent;
import com.aplicant.test.client.event.card.delete.DeleteContactHandler;
import com.aplicant.test.client.event.card.discard.DiscardContactEvent;
import com.aplicant.test.client.event.card.discard.DiscardContactHandler;
import com.aplicant.test.client.event.card.goback.GoBackEvent;
import com.aplicant.test.client.event.card.goback.GoBackHandler;
import com.aplicant.test.client.event.card.save.SaveContactEvent;
import com.aplicant.test.client.event.card.save.SaveContactHandler;
import com.aplicant.test.client.event.card.validate.name.ContactValidateNameEvent;
import com.aplicant.test.client.event.card.validate.name.ContactValidateNameHandler;
import com.aplicant.test.client.event.card.validate.phone.ContactValidatePhoneEvent;
import com.aplicant.test.client.event.card.validate.phone.ContactValidatePhoneHandler;
import com.aplicant.test.client.factory.ClientFactory;
import com.aplicant.test.client.place.phonebook.Phonebook;
import com.aplicant.test.client.view.card.CardView;
import com.aplicant.test.shared.model.Contact;
import com.aplicant.test.shared.validate.NameFieldVerifier;
import com.aplicant.test.shared.validate.PhoneFieldVerifier;

public abstract class CommonCardActivity extends CommonActivity {
	private ClientFactory clientFactory;
	private DispatchAsync dispatch;
	private CardView view;
	
}
