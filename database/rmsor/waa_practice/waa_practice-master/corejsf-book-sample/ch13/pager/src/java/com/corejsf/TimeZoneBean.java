package com.corejsf;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped; 
   // or import javax.faces.bean.RequestScoped;

@Named("tz") // or @ManagedBean(name="tz")
@RequestScoped
public class TimeZoneBean {
   private String[] data = java.util.TimeZone.getAvailableIDs();
}
