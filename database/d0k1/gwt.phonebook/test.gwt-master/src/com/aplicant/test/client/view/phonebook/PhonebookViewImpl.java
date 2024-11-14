package com.aplicant.test.client.view.phonebook;

import java.util.ArrayList;

import com.aplicant.test.client.event.phonebook.create.CreateContactEvent;
import com.aplicant.test.client.event.phonebook.delete.DeleteContactEvent;
import com.aplicant.test.client.event.phonebook.filter.FilterContactsEvent;
import com.aplicant.test.client.event.phonebook.unfilter.UnfilterContactsEvent;
import com.aplicant.test.client.event.phonebook.update.UpdateContactEvent;
import com.aplicant.test.shared.model.Contact;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class PhonebookViewImpl extends Composite implements PhonebookView {
	private ArrayList<Contact> currentContacts = new ArrayList<Contact>();

	private static PhonebookViewImplUiBinder uiBinder = GWT
			.create(PhonebookViewImplUiBinder.class);

	interface PhonebookViewImplUiBinder extends
			UiBinder<Widget, PhonebookViewImpl> {
	}

	@UiField Button addButton;
	@UiField Button viewButtom;
	@UiField Button deleteButtom;
	@UiField TextBox filterInput;
	@UiField ListBox contactList;
	@UiField Button filterButton;
	@UiField Button unfilterButton;
	private EventBus eventBus;
}
