package com.corejsf;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Named;
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
   // or import javax.faces.bean.ApplicationScoped;

@Named // or @ManagedBean
@ApplicationScoped
public class Dates implements Serializable {
   private int[] days;
   private int[] years;
   private Map<String, Integer> months;
}
