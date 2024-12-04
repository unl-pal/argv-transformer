/**
 * 
 */
package com.aplicant.test.client.view.welcome;

import com.aplicant.test.client.event.welcome.GotoBookEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author dOkI
 *
 */
public class WelcomeViewImpl extends Composite implements WelcomeView {
	private EventBus eventBus;
	private static WelcomeViewImplUiBinder uiBinder = GWT
			.create(WelcomeViewImplUiBinder.class);

	interface WelcomeViewImplUiBinder extends UiBinder<Widget, WelcomeViewImpl> {
	}

	@UiField Button button;
	@UiField InlineLabel GreetingsLabel;
}
