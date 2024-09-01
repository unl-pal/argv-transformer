/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.web;

import com.example.model.Tea;
import com.example.model.TeaExpert;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author rmsor_000
 */
@Named("tea")
@SessionScoped
public class TeaSelect implements Serializable {

    private TeaExpert teaExp;
    
    private double grandTotalPrice=0.00;
    
    private ArrayList<Tea> selTeas;
    
    
    

}
