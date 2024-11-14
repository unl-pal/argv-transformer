package com.company.util.querygenerator.select;

import org.apache.log4j.Logger;

import com.company.service.bean.SortFormBean;
import com.company.util.ClassNameUtil;

public class LimitGenerator extends Generator{

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private int page;
	private int onPage;
}
