package com.corejsf;

import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.corejsf.Card")
public class CreditCardConverter implements Converter, Serializable 
{
   private String separator;
}
