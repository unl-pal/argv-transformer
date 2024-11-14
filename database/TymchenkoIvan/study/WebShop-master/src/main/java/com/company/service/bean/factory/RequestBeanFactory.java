package com.company.service.bean.factory;

import java.util.EnumMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.company.service.bean.Bean;
import com.company.service.bean.RequestBeanType;

/**
 * Creates concrete Bean using RequestBeanCreators.
 * 
 * @author Ivan_Tymchenko
 *
 */
public class RequestBeanFactory {
	
	@SuppressWarnings("serial")
	private static Map<RequestBeanType, RequestBeanCreator> creators = new EnumMap<RequestBeanType, RequestBeanCreator>(RequestBeanType.class){{
		put(RequestBeanType.LOGIN_FORM, new LoginFormBeanCreator());
		put(RequestBeanType.ORDER_DETAILS_FORM, new OrderDetailsFormBeanCreator());
		put(RequestBeanType.REGISTER_FORM, new RegistrationFormBeanCreator());
		put(RequestBeanType.SORT_FORM, new SortFormBeanCreator());
	}};
}