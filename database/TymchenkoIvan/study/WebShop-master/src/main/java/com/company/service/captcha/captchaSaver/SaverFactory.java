package com.company.service.captcha.captchaSaver;

import java.util.EnumMap;

/**
 * Contains all Saver types and concrete classes.
 * 
 * @author Ivan_Tymchenko
 *
 */
public class SaverFactory {
	
	@SuppressWarnings("serial")
	public static final EnumMap<SaverType, Saver> SAVERS = new EnumMap<SaverType, Saver>(SaverType.class) {{
	    put(SaverType.SESSION, new SessionSaver());
	    put(SaverType.COOKIE, new CookieSaver());
	    put(SaverType.HIDDEN, new HiddenFieldSaver());
	}};
}

