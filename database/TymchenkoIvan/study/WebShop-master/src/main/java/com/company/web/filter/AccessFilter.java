package com.company.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.company.constant.AppScopeConstants;
import com.company.constant.Constants;
import com.company.entity.Role;
import com.company.entity.User;
import com.company.util.ClassNameUtil;
import com.company.util.FilterUtil;
import com.company.util.ViewPath;
import com.company.web.filter.access.SecurityDispatcher;

public class AccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	private SecurityDispatcher securityDispatcher;

}
