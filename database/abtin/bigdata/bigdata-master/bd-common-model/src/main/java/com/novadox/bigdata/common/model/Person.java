package com.novadox.bigdata.common.model;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private Date dateOfBirth;
}