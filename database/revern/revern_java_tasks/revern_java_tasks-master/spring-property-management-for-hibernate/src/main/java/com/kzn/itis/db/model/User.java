package com.kzn.itis.db.model;


import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    private int id;

    private String firstname;

    private String lastname;

    private int age;


}
