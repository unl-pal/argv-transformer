package com.corejsf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.inject.Named; 
   // or import javax.faces.bean.ManagedBean;
import javax.enterprise.context.RequestScoped; 
   // or import javax.faces.bean.RequestScoped;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

@Named // or @ManagedBean
@RequestScoped
public class CustomerBean {
   @Resource(name="jdbc/mydb")
   private DataSource ds;
}
