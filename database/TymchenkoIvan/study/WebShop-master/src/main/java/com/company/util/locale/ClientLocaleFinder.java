package com.company.util.locale;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.company.util.ClassNameUtil;

public class ClientLocaleFinder extends LocaleFinder {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	private List<String> appLocales;
	
	private Enumeration<Locale> clientLocales;
	private Locale matchesLocale;
}