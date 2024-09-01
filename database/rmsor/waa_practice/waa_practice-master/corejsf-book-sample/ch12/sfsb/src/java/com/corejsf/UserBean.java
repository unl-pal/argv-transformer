package com.corejsf;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named("user")
@SessionScoped
@Stateful
public class UserBean {
   private String name;
   private String password;
   private boolean loggedIn;
   private int count;

   @PersistenceContext(unitName="default")
   private EntityManager em;   
}
