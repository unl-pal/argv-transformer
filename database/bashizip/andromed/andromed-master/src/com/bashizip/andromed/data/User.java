package com.bashizip.andromed.data;

import java.io.Serializable;



public class User implements Serializable {

    private static final long serialVersionUID = 7390103290165670089L;
	private Long id;
	private String firstname;
	private String lastname;
	private String mail;
	private String phone;
	private String categorie;
	private int since;
	private boolean underTreatment;
	
	private int age;
	

}
