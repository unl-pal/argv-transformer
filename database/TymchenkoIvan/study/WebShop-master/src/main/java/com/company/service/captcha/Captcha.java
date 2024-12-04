package com.company.service.captcha;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Captcha class contains timeout in milliseconds and id.
 * Id contains creation time in milliseconds.
 * 
 * @author Ivan_Tymchenko
 *
 */
@SuppressWarnings("serial")
public class Captcha implements Serializable{
	
	private static long timeout;
	
    private long id;
}
