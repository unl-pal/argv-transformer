package com.company.util.locale;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.company.constant.Constants;
import com.company.util.ClassNameUtil;
import com.company.util.locale.handler.LocaleHandler;

public class SessionLocaleHandler extends LocaleHandler{

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private HttpSession session;

}
