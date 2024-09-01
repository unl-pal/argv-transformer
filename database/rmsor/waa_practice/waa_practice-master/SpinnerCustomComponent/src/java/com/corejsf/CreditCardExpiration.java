package com.corejsf;

import java.io.Serializable;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;

@SessionScoped
@Named("cardExpirationDate") // or @ManagedBean(name="cardExpirationDate")
public class CreditCardExpiration implements Serializable {
   private int month = 1;
   private int year = 2010;
}