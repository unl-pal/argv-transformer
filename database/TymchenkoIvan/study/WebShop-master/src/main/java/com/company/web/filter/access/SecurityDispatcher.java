package com.company.web.filter.access;

import org.apache.log4j.Logger;

import com.company.entity.security.Constraint;
import com.company.entity.security.Security;
import com.company.util.ClassNameUtil;

public class SecurityDispatcher {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());

	private Security security;
}
