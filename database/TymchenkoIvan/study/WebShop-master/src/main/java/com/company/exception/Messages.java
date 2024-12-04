package com.company.exception;

/**
 * Contains exception messages
 * 
 * @author Ivan_Tymchenko
 *
 */
public class Messages {
	
	public static final String VALIDATION_FIRST_NAME_WRONG = "Wrong first name";
	public static final String VALIDATION_LOGIN_WRONG = "Wrong login";
	public static final String VALIDATION_LOGIN_NOT_UNIQUE = "Wrong is not unique";
	public static final String VALIDATION_LAST_NAME_WRONG = "Wrong last name";
	public static final String VALIDATION_MAIL_WRONG = "Wrong mail";
	public static final String VALIDATION_PASS_WRONG = "Wrong password";
	public static final String VALIDATION_PASS_CONFIRM_WRONG = "Wrong password confirm";
	public static final String VALIDATION_CAPTCHA_WRONG = "Wrong captcha code";
	public static final String VALIDATION_SORT_WRONG = "Wrong sort request! Use only form!";
	public static final String VALIDATION_ORDER_DETAILS_WRONG = "Wrong details request! Use only form!";
	
	public static final String VALIDATION_ORDER_WRONG_EMPTY_BASKET = "Wrong order info. Basket is empty.";
	public static final String VALIDATION_ORDER_WRONG = "Wrong order info";
	
	public static final String DATA_CONN_NULL = "Connection is null";

	public static final String LOGIN_WRONG = "Wrong pair login/password";
	
	public static final String CANNOT_GET_OUTPUT_STREAM = "PrintWriter obtained already - cannot get OutputStream";
	public static final String STREAM_WAS_CLOSED = "Cannot work with a closed output stream";
	public static final String CANNOT_CREATE_WRITER = "Can not create writer";
	public static final String OUTPUT_STREAM_HAS_BEEN_CALLED = "getOutputStream() has already been called!";
	public static final String WRITER_HAS_BEEN_CALLED = "getWriter() has already been called!";
}
