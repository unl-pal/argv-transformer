package com.aplicant.test.server.handlers.get;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.aplicant.test.server.storage.ContactDAO;
import com.aplicant.test.shared.action.get.GetContactByIdAction;
import com.aplicant.test.shared.action.get.GetContactByIdResult;
import com.aplicant.test.shared.model.Contact;

public class GetContactByIdHandler implements ActionHandler<GetContactByIdAction, GetContactByIdResult>{

}
