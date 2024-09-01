package com.corejsf;

import java.util.Locale;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped; 
   // or import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@Named // or @ManagedBean
@RequestScoped
public class LocaleChanger {
   private String languageCode;
}
