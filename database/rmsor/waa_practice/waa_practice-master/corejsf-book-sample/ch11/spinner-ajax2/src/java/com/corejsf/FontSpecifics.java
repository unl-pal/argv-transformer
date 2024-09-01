package com.corejsf;

import java.io.Serializable;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;

@SessionScoped
@Named // or @ManagedBean
public class FontSpecifics implements Serializable {
   private int size = 1;
}