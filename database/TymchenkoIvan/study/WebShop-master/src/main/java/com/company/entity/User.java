package com.company.entity;

/**
 * User entity realization.
 * 
 * @author Ivan_Tymchenko
 */
public class User implements Entity{
	
	private long id;
	private long roleId;
	private String login;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String[] notifications;
}
