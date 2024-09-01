package com.corejsf;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;

@Named("bb") // or @ManagedBean(name="bb")
@SessionScoped
public class BackingBean implements Serializable {
   private int day;
   private int month;
   private int year;
}