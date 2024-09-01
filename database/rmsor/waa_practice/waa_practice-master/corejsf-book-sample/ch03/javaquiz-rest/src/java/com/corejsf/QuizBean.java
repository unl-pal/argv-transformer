package com.corejsf;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;

@Named // or @ManagedBean
@SessionScoped
public class QuizBean implements Serializable {
   private int currentProblem;
   private int tries;
   private String response = "";
   private String correctAnswer;

   // Here, we hardwire the problems. In a real application,
   // they would come from a database
   private ArrayList<Problem> problems = new ArrayList<Problem>(Arrays.asList(
      new Problem(
         "What trademarked slogan describes Java development? Write once, ...",
         "run anywhere"),
      new Problem(
         "What are the first 4 bytes of every class file (in hexadecimal)?",
         "CAFEBABE"),
      new Problem(
         "What does this statement print? System.out.println(1+\"2\");",
         "12"),
      new Problem(
         "Which Java keyword is used to define a subclass?",
         "extends"),
      new Problem(
         "What was the original name of the Java programming language?",
         "Oak"),
      new Problem(
         "Which java.util class describes a point in time?",
         "Date")));

   private int[] scores = new int[problems.size()];
}