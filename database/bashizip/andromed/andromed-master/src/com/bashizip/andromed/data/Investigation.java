package com.bashizip.andromed.data;

import java.io.Serializable;
import java.util.Date;



public class Investigation implements Serializable{

	private static final long serialVersionUID = 7590103290165670089L;
	
	private long id;
	private String desease;
	
	private String contry;
	private String zone;
	private String author;
	private String code;
	private int status;
	private String description;
	private Date startDate;
	private int syncStatus=0;
	
	
}
