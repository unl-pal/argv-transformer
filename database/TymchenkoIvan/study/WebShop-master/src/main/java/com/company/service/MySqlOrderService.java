package com.company.service;

import org.apache.log4j.Logger;

import com.company.db.JdbcTransactionManager;
import com.company.db.Operation;
import com.company.db.TransactionManager;
import com.company.db.dao.MySqlOrderDAO;
import com.company.db.dao.MySqlOrderItemDAO;
import com.company.db.dao.OrderDAO;
import com.company.db.dao.OrderItemDAO;
import com.company.exception.DataException;
import com.company.service.bean.OrderInfoBean;
import com.company.util.ClassNameUtil;

public class MySqlOrderService implements OrderService{
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private OrderDAO orderDao;
	private OrderItemDAO orderItemDao;
	private TransactionManager manager;
}
