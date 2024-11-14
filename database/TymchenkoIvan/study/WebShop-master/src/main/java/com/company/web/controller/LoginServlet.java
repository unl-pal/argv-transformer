package com.company.web.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.company.constant.AppScopeConstants;
import com.company.constant.Constants;
import com.company.entity.User;
import com.company.exception.DataException;
import com.company.exception.Messages;
import com.company.exception.ValidationException;
import com.company.service.UserService;
import com.company.service.bean.LoginFormBean;
import com.company.service.bean.RequestBeanType;
import com.company.service.bean.factory.RequestBeanFactory;
import com.company.util.ClassNameUtil;
import com.company.util.Validator;
import com.company.util.ViewPath;


@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet{

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private UserService userService;
}
