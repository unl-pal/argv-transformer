package com.company.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.company.db.JdbcHolder;
import com.company.exception.DataException;
import com.company.service.bean.OrderInfoBean;
import com.company.util.ClassNameUtil;

public class MySqlOrderDAO implements OrderDAO{
	
	private  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private static final String INSERT_NEW_ORDER = "INSERT INTO orders VALUES(DEFAULT, ?, ?, ?, 1, 'added by user', ?);";
}
