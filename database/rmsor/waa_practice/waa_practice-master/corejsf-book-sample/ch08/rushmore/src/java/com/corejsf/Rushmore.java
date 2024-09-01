package com.corejsf;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped; 
   // or import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named // or @ManagedBean
@RequestScoped
public class Rushmore {
   private String outcome = null;
   private Rectangle washingtonRect = new Rectangle(70, 30, 40, 40);
   private Rectangle jeffersonRect = new Rectangle(115, 45, 40, 40);
   private Rectangle rooseveltRect = new Rectangle(135, 65, 40, 40);
   private Rectangle lincolnRect = new Rectangle(175, 62, 40, 40);
}
