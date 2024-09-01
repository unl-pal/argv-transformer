package com.corejsf;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

@FacesRenderer(componentFamily="javax.faces.Input", 
   rendererType="com.corejsf.FontSpinner")
   
@ResourceDependencies( {   
   @ResourceDependency(library="javascript", name="spinner.js"),
   @ResourceDependency(library="javax.faces", name="jsf.js")   
})
public class FontSpinnerRenderer extends Renderer {
}
