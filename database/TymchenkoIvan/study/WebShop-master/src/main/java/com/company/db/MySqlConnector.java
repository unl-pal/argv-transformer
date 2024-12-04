package com.company.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.company.constant.DeployConstants;

/**
 * Contains connections for MySql database.
 * 
 * @author Ivan_Tymchenko
 */
public class MySqlConnector {
	
	private DataSource dataSource;
}
