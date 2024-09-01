/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.jsf.validators;

import application.jsf.BrowseSelectPCB;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Ant Ongun Kefeli
 */
@Named("quantityValidator")
@RequestScoped
public class QuantityValidator implements Validator {
    
    @Inject
    private BrowseSelectPCB bean;
    
}
