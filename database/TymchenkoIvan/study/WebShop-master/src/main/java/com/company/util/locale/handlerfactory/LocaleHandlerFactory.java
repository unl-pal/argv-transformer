package com.company.util.locale.handlerfactory;

import java.util.EnumMap;

import com.company.util.locale.CookieLocaleHandler;
import com.company.util.locale.SessionLocaleHandler;
import com.company.util.locale.handler.LocaleHandler;

public class LocaleHandlerFactory {
	
	@SuppressWarnings("serial")
	public static final EnumMap<LocaleHandlerType, LocaleHandler> HANDLERS = new EnumMap<LocaleHandlerType, LocaleHandler>(LocaleHandlerType.class) {{
	    put(LocaleHandlerType.SESSION, new SessionLocaleHandler());
	    put(LocaleHandlerType.COOKIE, new CookieLocaleHandler());
	}};
}
