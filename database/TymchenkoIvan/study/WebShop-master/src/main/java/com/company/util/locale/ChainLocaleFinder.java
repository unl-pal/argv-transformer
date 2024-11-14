package com.company.util.locale;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.company.util.ClassNameUtil;

public class ChainLocaleFinder{	
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private LocaleFinder choosenFinder;
	private ClientLocaleFinder clients;
	private DefaultLocaleFinder def;
}
