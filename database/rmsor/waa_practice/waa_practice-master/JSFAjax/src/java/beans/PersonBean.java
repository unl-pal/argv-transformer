/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author 984317
 */
import entities.Person;
import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;

import javax.inject.Named;

@Named("pb")

@RequestScoped

public class PersonBean implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String streetAddress;
    private String zip;
    private String state;
    private String msg;
    private String msgColor;

    private ArrayList<Person> persons = new ArrayList();

}
