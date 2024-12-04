package com.aplicant.test.client.event.card.delete;

import com.aplicant.test.shared.model.Contact;
import com.google.gwt.event.shared.GwtEvent;

public class DeleteContactEvent extends GwtEvent<DeleteContactHandler> {
	public static Type<DeleteContactHandler> TYPE = new Type<DeleteContactHandler>();

	private Contact contact;
}
