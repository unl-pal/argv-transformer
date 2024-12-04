package com.company.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.constant.AppScopeConstants;
import com.company.constant.Constants;
import com.company.exception.DataException;
import com.company.exception.Messages;
import com.company.exception.ValidationException;
import com.company.service.ProductService;
import com.company.service.bean.ProductBean;
import com.company.service.bean.RequestBeanType;
import com.company.service.bean.SortFormBean;
import com.company.service.bean.factory.RequestBeanFactory;
import com.company.util.ClassNameUtil;
import com.company.util.Validator;
import com.company.util.ViewPath;

@SuppressWarnings("serial")
public class ProductsServlet extends HttpServlet{

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private ProductService productService;
	
	private int setPages(int productsCount, int onPage){
		
		if(productsCount % onPage == 0){
			return productsCount % onPage;
		} else{
			return productsCount / onPage + 1;
		}
	}
}