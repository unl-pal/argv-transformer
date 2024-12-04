package com.company.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.constant.AppScopeConstants;
import com.company.constant.Constants;
import com.company.constant.DeployConstants;
import com.company.util.ClassNameUtil;
import com.company.util.FilterUtil;
import com.company.util.locale.ChainLocaleFinder;
import com.company.util.locale.handler.LocaleHandler;
import com.company.web.filter.locale.LocaleRequestWrapper;

public class LocaleFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	private List<String> appLocales;
	private LocaleHandler localeHandler;
	private int cookieTimeout;
}
