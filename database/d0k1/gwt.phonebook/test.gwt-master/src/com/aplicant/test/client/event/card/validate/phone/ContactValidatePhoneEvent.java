package com.aplicant.test.client.event.card.validate.phone;

import com.google.gwt.event.shared.GwtEvent;

public class ContactValidatePhoneEvent extends GwtEvent<ContactValidatePhoneHandler> {
	public static Type<ContactValidatePhoneHandler> TYPE = new Type<ContactValidatePhoneHandler>();

	private String phone;
}
