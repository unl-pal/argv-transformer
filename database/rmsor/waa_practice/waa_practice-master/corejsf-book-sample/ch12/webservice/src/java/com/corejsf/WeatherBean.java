package com.corejsf;

import java.util.List;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.xml.ws.WebServiceRef;

import com.corejsf.ws.Forecast;
import com.corejsf.ws.ForecastReturn;
import com.corejsf.ws.Weather;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named // or @ManagedBean
@SessionScoped
public class WeatherBean implements Serializable {
   @WebServiceRef(wsdlLocation="http://ws.cdyne.com/WeatherWS/Weather.asmx?wsdl")
   private Weather service;
   
   private String zip;
   private String city;
   private List<Forecast> response;
}
