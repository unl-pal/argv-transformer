package com.corejsf;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;

@Named // or @ManagedBean
@SessionScoped
public class QuizBean implements Serializable {
   private ArrayList<ProblemBean> problems = new ArrayList<ProblemBean>();     
   private int currentIndex;
   private int score;   
}
