package com.company.entity.security;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "security")
public class Security {

	private List<Constraint> constraints;
}
