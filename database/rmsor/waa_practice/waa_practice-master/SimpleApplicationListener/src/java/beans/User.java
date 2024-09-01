/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Person;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;


/**
 *
 * @author 984317
 */
@Named("user")
@SessionScoped
public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String result;
    private String resultV2;
    
    private ArrayList<Person> persons;
    
    
    
    
    
}
