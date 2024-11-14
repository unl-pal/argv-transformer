package com.company.service;

import com.company.db.JdbcTransactionManager;
import com.company.db.Operation;
import com.company.db.TransactionManager;
import com.company.db.dao.MySqlUserDAO;
import com.company.db.dao.UserDAO;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.exception.DataException;
import com.company.exception.ValidationException;
import com.company.service.bean.RegistrationFormBean;

/**
 * Service works with user.
 * 
 * @author Ivan_Tymchenko
 */
public class MySqlUserService implements UserService{

	private UserDAO userDao;
	private TransactionManager manager;
}
