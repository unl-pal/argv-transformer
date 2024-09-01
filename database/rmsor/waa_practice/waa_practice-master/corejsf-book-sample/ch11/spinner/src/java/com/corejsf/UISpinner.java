package com.corejsf;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.IntegerConverter;

@FacesComponent("com.corejsf.Spinner")
public class UISpinner extends UIInput {
   private static final String MORE = ".more";
   private static final String LESS = ".less";
}
