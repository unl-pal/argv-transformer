package com.aplicant.test.client.activity.phonebook;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.aplicant.test.client.activity.CommonActivity;
import com.aplicant.test.client.event.phonebook.create.CreateContactEvent;
import com.aplicant.test.client.event.phonebook.delete.DeleteContactEvent;
import com.aplicant.test.client.event.phonebook.delete.DeleteContactHandler;
import com.aplicant.test.client.event.phonebook.filter.FilterContactsEvent;
import com.aplicant.test.client.event.phonebook.filter.FilterContactsHandler;
import com.aplicant.test.client.event.phonebook.unfilter.UnfilterContactsEvent;
import com.aplicant.test.client.event.phonebook.unfilter.UnfilterContactsHandler;
import com.aplicant.test.client.event.phonebook.update.UpdateContactEvent;
import com.aplicant.test.client.event.phonebook.update.UpdateContactHandler;
import com.aplicant.test.client.factory.ClientFactory;
import com.aplicant.test.client.place.create.Create;
import com.aplicant.test.client.place.details.Details;
import com.aplicant.test.client.place.phonebook.Phonebook;
import com.aplicant.test.client.place.welcome.Welcome;
import com.aplicant.test.client.view.phonebook.PhonebookView;
import com.aplicant.test.shared.action.delete.DeleteContactAction;
import com.aplicant.test.shared.action.delete.DeleteContactResult;
import com.aplicant.test.shared.action.get.GetContactsAction;
import com.aplicant.test.shared.action.get.GetContactsResult;
import com.aplicant.test.shared.model.Contact;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PhonebookActivity extends CommonActivity {
	private ClientFactory clientFactory;
	private Phonebook place;
	private DispatchAsync dispatch;
	private final PhonebookView view;
}
