// TODO: Remove

package com.corejsf.util;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

public class MethodEvent extends FacesEvent {
   private MethodExpression method;
   private Object[] args;
}