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

import org.apache.log4j.Logger;

import com.company.util.ClassNameUtil;
import com.company.web.filter.gzip.GZipResponseWrapper;

public class GZipFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	public static final String ACCEPT_ENCODING = "accept-encoding";
	public static final String GZIP = "gzip";
}