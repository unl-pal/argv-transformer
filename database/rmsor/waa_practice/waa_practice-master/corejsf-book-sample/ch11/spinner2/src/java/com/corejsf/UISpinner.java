package com.corejsf;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.FacesListener;

@FacesComponent("com.corejsf.Spinner")
public class UISpinner extends UIInput {
   private FacesListener maxMinListener;
}
 