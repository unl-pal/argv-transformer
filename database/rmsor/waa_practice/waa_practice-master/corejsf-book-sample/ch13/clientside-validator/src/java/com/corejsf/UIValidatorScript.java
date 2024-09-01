package com.corejsf;

import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;

import javax.faces.application.ResourceDependency;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.validator.Validator;

@FacesComponent("com.corejsf.ValidatorScript")
@ResourceDependency(library="javascript", name="validateCreditCard.js", 
   target="head")
public class UIValidatorScript extends UIComponentBase {
   private Map<String, Validator> validators 
      = new LinkedHashMap<String, Validator>();   
}
