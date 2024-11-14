package com.company.web.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.company.constant.AppScopeConstants;
import com.company.constant.DeployConstants;
import com.company.entity.security.Security;
import com.company.service.MySqlBasketService;
import com.company.service.MySqlOrderService;
import com.company.service.MySqlProductService;
import com.company.service.MySqlUserService;
import com.company.service.captcha.Captcha;
import com.company.service.captcha.captchaSaver.SaverFactory;
import com.company.service.captcha.captchaSaver.SaverType;
import com.company.util.ClassNameUtil;
import com.company.util.locale.handlerfactory.LocaleHandlerFactory;
import com.company.util.locale.handlerfactory.LocaleHandlerType;
import com.company.web.filter.access.SecurityDispatcher;
import com.company.web.filter.access.SecurityXmlParser;

/**
 * ContextListener for application.
 * 
 * @author Ivan_Tymchenko
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	private String resourcesFolder;
}
