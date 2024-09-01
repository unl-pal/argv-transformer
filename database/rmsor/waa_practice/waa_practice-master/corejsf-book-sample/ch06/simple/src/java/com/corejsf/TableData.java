package com.corejsf;

import java.io.Serializable;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

@Named // or @ManagedBean
@SessionScoped
public class TableData implements Serializable {
   private static final Name[] names = new Name[] {
       new Name("William", "Dupont"),
       new Name("Anna", "Keeney"),
       new Name("Mariko", "Randor"),
       new Name("John", "Wilson")
   };
}
