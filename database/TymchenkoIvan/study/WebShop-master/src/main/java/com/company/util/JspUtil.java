package com.company.util;

import java.util.List;

import org.apache.log4j.Logger;

public class JspUtil {
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private static final String PAGE_REGEX = "[?,&]page=\\d*";
	private static final String PAGE_PARAMETER = "page=";
	private static final String PAGE_PRODUCTS = "/products?";
	
	private static final String LANG_REGEX = "[?,&]lang=\\w*";
	private static final String LANG_PARAMETER = "lang=";
}
