package co.pablobastidasv.controller;

import co.pablobastidasv.boundary.ClienteBoundary;

import javax.ejb.EJBAccessException;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by pbastidas on 31/03/15.
 */
@Model
public class AdminController {

	@Inject
	ClienteBoundary clienteBoundary;
}
