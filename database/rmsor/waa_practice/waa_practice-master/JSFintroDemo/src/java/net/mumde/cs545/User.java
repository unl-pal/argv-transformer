/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mumde.cs545;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author 984317
 */
import java.io.Serializable;

import javax.inject.Named;

import javax.enterprise.context.SessionScoped;


@Named("usr")

@SessionScoped

public class User implements Serializable

{

    private boolean bError = false;

    private String name;

    private String password;

}
