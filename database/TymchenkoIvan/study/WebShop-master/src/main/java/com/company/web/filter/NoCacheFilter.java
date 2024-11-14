package com.company.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.util.ClassNameUtil;

public class NoCacheFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
}