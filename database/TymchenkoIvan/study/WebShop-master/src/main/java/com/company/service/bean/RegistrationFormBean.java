package com.company.service.bean;

/**
 * Bean object contains fields from registration form.
 * 
 * @author Ivan_Tymchenko
 *
 */
public class RegistrationFormBean implements Bean{
	
	private static final long serialVersionUID = 6669036688120766276L;
	
	private String firstName;
	private String lastName;
	private String login;
	private String mail;
	private String password;
	private String rePassword;
	private String captchaCode;
	private String[] notifications;
	
}
