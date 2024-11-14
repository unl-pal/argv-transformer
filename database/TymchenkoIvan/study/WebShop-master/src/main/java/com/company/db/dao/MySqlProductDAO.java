package com.company.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.company.constant.DBConstants;
import com.company.db.JdbcHolder;
import com.company.entity.Product;
import com.company.exception.DataException;
import com.company.util.ClassNameUtil;

public class MySqlProductDAO implements ProductDAO{

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private static final String GET_ALL_PRODUCTS = "SELECT * FROM products";
	private static final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
}