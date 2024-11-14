package com.company.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.company.db.JdbcHolder;
import com.company.exception.DataException;
import com.company.service.bean.OrderInfoBean;
import com.company.service.bean.ProductBean;
import com.company.util.ClassNameUtil;

public class MySqlOrderItemDAO implements OrderItemDAO {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	private static final String INSERT_NEW_ORDER_ITEM = "INSERT INTO orderItems VALUES(DEFAULT, ?, ?, ?, ?);";
}
