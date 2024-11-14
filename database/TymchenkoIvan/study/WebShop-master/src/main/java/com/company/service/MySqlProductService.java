package com.company.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.company.db.JdbcTransactionManager;
import com.company.db.Operation;
import com.company.db.TransactionManager;
import com.company.db.dao.MySqlProductDAO;
import com.company.db.dao.ProductDAO;
import com.company.entity.Product;
import com.company.exception.DataException;
import com.company.service.bean.ProductBean;
import com.company.service.bean.SortFormBean;
import com.company.util.BeanUtil;
import com.company.util.ClassNameUtil;
import com.company.util.count.CountQueryGenerator;
import com.company.util.querygenerator.select.ChainGenerator;

public class MySqlProductService implements ProductService{

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private ProductDAO productDao;
	private TransactionManager manager;
	
	private String selectQuery;
	private String countQuery;
}