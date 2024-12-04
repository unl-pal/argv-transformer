package com.company.db;

import java.sql.Connection;

/**
 * Holds connection in ThreadLocal
 * 
 * @author Ivan_Tymchenko
 */
public final class JdbcHolder {
	
	private static ThreadLocal<Connection> holder = new ThreadLocal<>();


}
