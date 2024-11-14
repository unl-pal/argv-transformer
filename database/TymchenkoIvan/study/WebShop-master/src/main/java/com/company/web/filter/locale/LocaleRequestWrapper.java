package com.company.web.filter.locale;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class LocaleRequestWrapper extends HttpServletRequestWrapper {	
	
    private final Locale locale;	
}

