package com.aplicant.test.client.event.phonebook.update;

import com.aplicant.test.shared.model.Contact;
import com.google.gwt.event.shared.GwtEvent;

public class UpdateContactEvent extends GwtEvent<UpdateContactHandler> {
	private Contact contact;
	
	public static Type<UpdateContactHandler> TYPE = new Type<UpdateContactHandler>();
}
