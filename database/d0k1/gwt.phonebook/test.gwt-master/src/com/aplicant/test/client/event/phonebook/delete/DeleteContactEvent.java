package com.aplicant.test.client.event.phonebook.delete;

import com.aplicant.test.shared.model.Contact;
import com.google.gwt.event.shared.GwtEvent;

public class DeleteContactEvent extends GwtEvent<DeleteContactHandler> {
	private Contact contact;
	
	public static Type<DeleteContactHandler> TYPE = new Type<DeleteContactHandler>();
}
