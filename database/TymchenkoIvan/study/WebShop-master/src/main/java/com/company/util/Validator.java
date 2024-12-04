package com.company.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.company.constant.Constants;
import com.company.entity.Delivery;
import com.company.entity.Payment;
import com.company.exception.Messages;
import com.company.exception.ValidationException;
import com.company.service.bean.LoginFormBean;
import com.company.service.bean.OrderDetailsFormBean;
import com.company.service.bean.OrderInfoBean;
import com.company.service.bean.ProductBean;
import com.company.service.bean.RegistrationFormBean;
import com.company.service.bean.SortFormBean;
import com.company.service.captcha.captchaSaver.Saver;

/**
 * Helps to validate different information.
 * 
 * @author Ivan_Tymchenko
 *
 */
public class Validator {
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
}
