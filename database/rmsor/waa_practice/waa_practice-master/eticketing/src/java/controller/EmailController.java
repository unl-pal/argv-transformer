package controller;

import ejb.EmailSessionBean;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author xtrememe
 */
@ManagedBean
@RequestScoped
public class EmailController extends BaseController{
    @EJB
    private EmailSessionBean emailSessionBean;
    private String email;
}
