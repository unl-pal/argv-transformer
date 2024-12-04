package com.company.entity.security;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "constraint")
public class Constraint {
	
	private String urlPattern; 
	
	private List<String> roles;
}
