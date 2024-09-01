package com.corejsf;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped; 
   // or import javax.faces.bean.SessionScoped;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named // or @ManagedBean
@SessionScoped
public class NewAccount implements Serializable {
   private String name;
   private String emailAddress;
   private String password;

   @Resource(name="mail/gmailAccount")
   private Session mailSession;
}
