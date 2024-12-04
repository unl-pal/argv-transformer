package com.company.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.company.constant.AppScopeConstants;
import com.company.constant.Constants;
import com.company.exception.DataException;
import com.company.exception.Messages;
import com.company.exception.ValidationException;
import com.company.service.UserService;
import com.company.service.bean.RegistrationFormBean;
import com.company.service.bean.RequestBeanType;
import com.company.service.bean.factory.RequestBeanFactory;
import com.company.service.captcha.captchaSaver.Saver;
import com.company.util.Validator;
import com.company.util.ViewPath;

/**
 * Obtains registration form reguest.
 * 
 * @author Ivan_Tymchenko
 */
@MultipartConfig(
		fileSizeThreshold = 1024*1024*1, 
		maxFileSize = 1024*1024*1, 
		maxRequestSize = 1024*1024*1)
@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	private UserService userService;
	private Saver captchaSaver;
}