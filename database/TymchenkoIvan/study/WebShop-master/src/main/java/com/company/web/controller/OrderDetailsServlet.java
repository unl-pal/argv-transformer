package com.company.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.company.constant.Constants;
import com.company.service.bean.OrderDetailsFormBean;
import com.company.service.bean.RequestBeanType;
import com.company.service.bean.factory.RequestBeanFactory;
import com.company.util.ClassNameUtil;
import com.company.util.Validator;
import com.company.util.ViewPath;

@SuppressWarnings("serial")
public class OrderDetailsServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
}