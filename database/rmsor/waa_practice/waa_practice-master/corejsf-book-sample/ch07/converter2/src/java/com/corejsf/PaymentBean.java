package com.corejsf;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;

@Named("payment") // or @ManagedBean(name="payment")
@SessionScoped
public class PaymentBean implements Serializable {
   private double amount;
   private CreditCard card = new CreditCard("");
   private Date date = new Date();
}
