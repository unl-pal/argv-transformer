package com.company.util.count;

import org.apache.log4j.Logger;

import com.company.util.ClassNameUtil;

/**
 * Changes select query to select count. 
 * 
 * @author Ivan_Tymchenko
 *
 */
public class CountQueryGenerator {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private static final String SELECT_COMMAND = "*";
	private static final String COUNT_COMMAND = "COUNT(*)";
}