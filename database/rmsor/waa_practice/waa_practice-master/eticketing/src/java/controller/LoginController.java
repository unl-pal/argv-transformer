package controller;

import ejb.UserFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import model.UserInfo;

/**
 *
 * @author xtrememe
 */
@ManagedBean
@SessionScoped
public class LoginController extends BaseController{
    @EJB
    private UserFacade userFacade;
    private UserInfo user;
    private String email;
    
    
}

// http://www.pramati.com/docstore/1270002/index.htm
// http://www.avajava.com/tutorials/lessons/how-do-i-create-a-login-module.html
// http://stackoverflow.com/questions/20396276/jaas-exception-null-username-and-password
