package com.company.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.company.constant.DBConstants;
import com.company.db.JdbcHolder;
import com.company.db.dao.UserDAO;
import com.company.entity.User;
import com.company.exception.DataException;
import com.company.util.ClassNameUtil;

/**
 * Implements UserDAO interface and works with MySql database.
 * 
 * @author Ivan_Tymchenko
 */
public class MySqlUserDAO implements UserDAO {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	private static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
	private static final String INSERT_NEW_USER = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_NEW_NOTIFACATION = "INSERT INTO subs VALUES(DEFAULT, ?, ?);";
}