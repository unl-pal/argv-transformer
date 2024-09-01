package com.corejsf;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@Named("form") // or @ManagedBean(name="form")
@SessionScoped
public class RegisterForm implements Serializable {
   private String streetAddress;
   private String city;
   private String state;
   private String country;

   private static final Locale[] countries = { Locale.US, Locale.CANADA };
}
