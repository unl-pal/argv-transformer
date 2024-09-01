package com.corejsf;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Credentials implements Serializable {
   @Id
   private String username;
   private String passwd;
   private int loginCount;   
}