package com.corejsf;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Named("payment") // or @ManagedBean(name="payment")
@SessionScoped
public class PaymentBean implements Serializable {
   @Min(10) @Max(10000)
   private double amount;
   @Size(min=13,message="{com.corejsf.creditCardLength}") @LuhnCheck
   private String card = "";
   @Future
   private Date date = new Date();
}
