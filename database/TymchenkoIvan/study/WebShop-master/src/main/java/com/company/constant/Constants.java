package com.company.constant;

/**
 * Contains application constants.
 * 
 * @author Ivan_Tymchenko
 */
public class Constants {
	
	public static final String CONTENT_TYPE_IMAGE = "image/jpg";

	public static final String CAPTCHA_ID_ARGS = "captchaId";
	
	public static final String FORM_FIRST_NAME = "firstName";
	public static final String FORM_LAST_NAME = "lastName";
	public static final String FORM_LOGIN = "login";
	public static final String FORM_MAIL = "mail";
	public static final String FORM_PASSWORD = "password";
	
	public static final String FORM_RE_PASSWORD = "re_password";
	public static final String FORM_CAPTCHA_ID = "captchaId";
	public static final String FORM_CAPTCHA_CODE = "captchaCode";
	public static final String FORM_NOTIFICATION = "notification";
	public static final String FORM_AVATAR = "avatar";
	
	public static final String SORT_FORM_SEARCH = "search";
	public static final String SORT_FORM_SORT = "sort";
	public static final String SORT_FORM_TYPES = "type";
	public static final String SORT_FORM_BRANDS = "brand";
	public static final String SORT_FORM_ON_PAGE = "on_page";
	public static final String SORT_FORM_MIN_PRICE = "min_price";
	public static final String SORT_FORM_MAX_PRICE = "max_price";
	public static final String SORT_FORM_PAGE_NUMBER = "page";
	
	public static final String ORDER_DETAILS_FORM_DELIVERY = "delivery";
	public static final String ORDER_DETAILS_FORM_PAYMENT = "payment";
	
	public static final String PRODUCT_ID = "id";
	
	public static final String LOCALE = "locale";
	
	public static final String PATTERN_MAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,30})$";
	public static final String PATTERN_NAME="^[A-Z\u0410-\u042f][a-z\u0430-\u044f]{2,20}$";
	public static final String PATTERN_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
	public static final String PATTERN_NUMBER = "\\d+";
	
	public static final int MAX_ITEMS_ON_PAGE = 50;
	public static final int DEFAULT_ITEMS_ON_PAGE = 6;
	public static final int DEFAULT_PAGE = 1;

	//vars in web
	public static final String WEB_LOGIN_BEAN = "loginBean";
	public static final String WEB_REGISTER_BEAN = "bean";
	public static final String WEB_SORT_BEAN = "sortBean";
	public static final String WEB_PRODUCT_BEANS = "products";
	public static final String WEB_PRODUCT_BEAN = "product";
	public static final String WEB_PRODUCTS_COUNT = "productsCount";
	public static final String WEB_USER = "user";
	public static final String WEB_COUNT_ON_PAGE = "onPage";
	public static final String WEB_PAGE = "page";
	public static final String WEB_PAGES = "pages";
	public static final String WEB_OLD_URL = "url";
	public static final String WEB_ORDER_ID = "orderId";
	public static final String WEB_LOCALE = "lang";
	
	public static final String WEB_SESSION_BASKET = "basket";
	public static final String WEB_SESSION_ORDER_DETAILS = "details";
	
	public static final String ERROR_EXCEPTIONS_MAP = "exceptions";
	public static final String ERROR_MESSAGE_NAME = "error";
	public static final String REGISTER_ERROR_MESSAGE_VALUE = "Please, enter info again!";
	
	//photos vars
	public static final String IMAGE_DIR = "D:\\images";
	public static final String AVATAR_FILE = "avatar.png";
	public static final String AVATAR_DEFAULT_FILE = "default_avatar.png";
	public static final String PRODUCT_DEFAULT_FILE = "default_product.png";
	public static final String PRODUCT_IMAGE_DIR = "D:\\images\\products";
	
	public static final String DIR_LOCALES = "locales";
}
