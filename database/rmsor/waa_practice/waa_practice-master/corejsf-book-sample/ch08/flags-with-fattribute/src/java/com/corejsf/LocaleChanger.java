package com.corejsf;

import java.util.Locale;
import java.util.Map;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped; 
   // or import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named // or @ManagedBean
@RequestScoped
public class LocaleChanger {
}
