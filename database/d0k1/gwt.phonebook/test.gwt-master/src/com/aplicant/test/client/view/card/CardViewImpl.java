package com.aplicant.test.client.view.card;

import com.aplicant.test.client.event.card.delete.DeleteContactEvent;
import com.aplicant.test.client.event.card.discard.DiscardContactEvent;
import com.aplicant.test.client.event.card.save.SaveContactEvent;
import com.aplicant.test.client.event.card.validate.name.ContactValidateNameEvent;
import com.aplicant.test.client.event.card.validate.phone.ContactValidatePhoneEvent;
import com.aplicant.test.shared.model.Contact;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

public class CardViewImpl extends Composite implements CardView {

	private String contactId = null;
	private static CardViewImplUiBinder uiBinder = GWT
			.create(CardViewImplUiBinder.class);
	@UiField TextBox nameField;
	@UiField TextBox phoneField;
	@UiField Button saveButton;
	@UiField Button cancelButton;
	@UiField Button deleteButton;
	@UiField Label nameLabel;
	@UiField Label phoneLabel;
	private EventBus eventBus;

	interface CardViewImplUiBinder extends UiBinder<Widget, CardViewImpl> {
	}

}
