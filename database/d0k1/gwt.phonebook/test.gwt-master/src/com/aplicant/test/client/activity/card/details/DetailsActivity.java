package com.aplicant.test.client.activity.card.details;

import com.aplicant.test.client.activity.card.CommonCardActivity;
import com.aplicant.test.client.factory.ClientFactory;
import com.aplicant.test.client.place.details.Details;
import com.aplicant.test.shared.action.delete.DeleteContactAction;
import com.aplicant.test.shared.action.delete.DeleteContactResult;
import com.aplicant.test.shared.action.get.GetContactByIdAction;
import com.aplicant.test.shared.action.get.GetContactByIdResult;
import com.aplicant.test.shared.action.update.UpdateContactAction;
import com.aplicant.test.shared.action.update.UpdateContactResult;
import com.aplicant.test.shared.model.Contact;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class DetailsActivity extends CommonCardActivity {
	private Details place;
	//private Contact currentContact = null;

}
