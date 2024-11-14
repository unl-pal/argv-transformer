package com.bashizip.andromed.data;

import java.io.Serializable;




public class Post implements Serializable {

	private static final long serialVersionUID = 7390103290165670089L;
	
	
	private Long id;

	//user
	private String firstname;
	private String lastname;
	private String mail;
	private String phone;
	
	//zone	
	private String pays;
	private String zone;
	private String description;

	
	//desease	
	private String desease;
	
	
	//patient
	private String name;	
	private String categorie;
	private int age;
	private int  sinceDays;
	private boolean underTreatment;
	
	private String postDate;
	
	private int syncStatus=0;



	

	

}
