package com.company.util.locale.handler;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.company.constant.Constants;
import com.company.util.locale.LocaleFinder;
import com.company.util.locale.LocaleSaver;

public abstract class LocaleHandler extends LocaleFinder implements LocaleSaver{
	
	private List<String> appLocales;
	protected int cookieTimeout;

}
