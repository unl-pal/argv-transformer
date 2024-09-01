package controller;

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
public class BaseController {
    private ExternalContext ec;
    private Flash flash;
    
}
