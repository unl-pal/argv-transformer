package com.corejsf;

import java.io.Serializable;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

@Named("tp") // or @ManagedBean(name="tp")
@SessionScoped
public class TabbedPane implements Serializable {
   private int index;
   private static final int JEFFERSON_INDEX = 0;
   private static final int ROOSEVELT_INDEX = 1; 
   private static final int LINCOLN_INDEX = 2;
   private static final int WASHINGTON_INDEX = 3;

   private String[] tabTooltips = { "jeffersonTooltip", "rooseveltTooltip",
         "lincolnTooltip",  "washingtonTooltip" };
}