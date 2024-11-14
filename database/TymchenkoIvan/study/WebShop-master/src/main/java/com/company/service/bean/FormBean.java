package com.company.service.bean;

import java.io.Serializable;

/**
 * Bean object contains fields from registration form.
 * 
 * @author Ivan_Tymchenko
 *
 */
@SuppressWarnings("serial")
public class FormBean implements Serializable{
	
	private String firstName;
	private String lastName;
	private String login;
	private String mail;
	private String password;
	private String rePassword;
	private String captchaCode;
	private String[] notifications;
	
}
