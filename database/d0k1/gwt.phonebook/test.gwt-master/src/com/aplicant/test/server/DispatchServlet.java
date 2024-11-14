package com.aplicant.test.server;

import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.InstanceActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import com.aplicant.test.server.handlers.create.CreateContactHandler;
import com.aplicant.test.server.handlers.delete.DeleteContactsHandler;
import com.aplicant.test.server.handlers.get.GetContactByIdHandler;
import com.aplicant.test.server.handlers.get.GetContactsHandler;
import com.aplicant.test.server.handlers.update.UpdateContactsHandler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DispatchServlet extends RemoteServiceServlet implements
		StandardDispatchService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Dispatch dispatch;

}
