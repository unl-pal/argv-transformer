package com.company.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.company.exception.DataException;
import com.company.exception.Messages;
import com.company.util.ClassNameUtil;
import com.company.util.DbUtils;

import org.apache.log4j.Logger;

/**
 * TransactionManager realization for JDBC
 * 
 * @author Ivan_Tymchenko
 *
 */
public class JdbcTransactionManager implements TransactionManager {
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private MySqlConnector connector;
}
