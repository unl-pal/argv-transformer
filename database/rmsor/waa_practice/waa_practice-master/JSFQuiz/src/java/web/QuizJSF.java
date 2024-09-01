/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import src.Quiz;

/**
 *
 * @author 984317
 */
@Named("QuizJSF")
@SessionScoped
public class QuizJSF implements Serializable {

    Quiz myQuiz;
    String txtAnswer;
    String msg;
    int noOfTries = 0;

}
