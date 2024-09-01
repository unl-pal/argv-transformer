package com.corejsf;

import java.awt.Color;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@Named("form") // or @ManagedBean(name="form")
@SessionScoped
public class RegisterForm implements Serializable {
   public enum Education { HIGH_SCHOOL, BACHELOR, MASTER, DOCTOR };

   public static class Weekday {
      private int dayOfWeek;
   }
   
   private String name;
   private boolean contactMe;
   private int[] bestDaysToContact;
   private Integer yearOfBirth;
   private int[] colors;
   private Set<String> languages = new TreeSet<String>();
   private Education education = Education.BACHELOR;

   private SelectItem[] colorItems = {
      new SelectItem(Color.RED.getRGB(), "Red"), // value, label
      new SelectItem(Color.GREEN.getRGB(), "Green"),
      new SelectItem(Color.BLUE.getRGB(), "Blue"),
      new SelectItem(Color.YELLOW.getRGB(), "Yellow"),
      new SelectItem(Color.ORANGE.getRGB(), "Orange", "", true) // disabled
   };

   private static Map<String, Education> educationItems;
   static {
      educationItems = new LinkedHashMap<String, Education>();
      educationItems.put("High School", Education.HIGH_SCHOOL); // label, value
      educationItems.put("Bachelor's", Education.BACHELOR);
      educationItems.put("Master's", Education.MASTER);
      educationItems.put("Doctorate", Education.DOCTOR);
   };

   private static SelectItem[] languageItems = {
      new SelectItem("English"),
      new SelectItem("French"),
      new SelectItem("Russian"),
      new SelectItem("Italian"),
      new SelectItem("Esperanto", "Esperanto", "", true) // disabled
   };
   
   private static Collection<SelectItem> birthYears;
   static {
      birthYears = new ArrayList<SelectItem>();
      // The first item is a "no selection" item
      birthYears.add(new SelectItem(null, "Pick a year:", "", false, false, true));
      for (int i = 1900; i < 2020; ++i) birthYears.add(new SelectItem(i));
   }
      
   private static Weekday[] daysOfTheWeek;
   static {
      daysOfTheWeek = new Weekday[7];
      for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
         daysOfTheWeek[i - Calendar.SUNDAY] = new Weekday(i); 
      }      
   }
}
