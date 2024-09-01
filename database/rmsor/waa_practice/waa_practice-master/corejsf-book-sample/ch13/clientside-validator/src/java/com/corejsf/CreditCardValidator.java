package com.corejsf;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.corejsf.CreditCard")
public class CreditCardValidator implements Validator, Serializable {
   private String message;
   private String arg;
}
