package com.corejsf;

import java.io.IOException;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.PhaseId;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

@FacesRenderer(componentFamily="javax.faces.Input", 
   rendererType="com.corejsf.Spinner")
public class SpinnerRenderer extends Renderer {
   private static final String MORE = ".more";
   private static final String LESS = ".less";
}
