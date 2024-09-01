package com.corejsf;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@Named // or @ManagedBean
@SessionScoped
public class LocaleChanger implements Serializable {
}
