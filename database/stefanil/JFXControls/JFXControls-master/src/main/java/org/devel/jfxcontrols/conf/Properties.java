/**
 * 
 */
package org.devel.jfxcontrols.conf;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author stefan.illgen
 */
public class Properties extends java.util.Properties {

	private static final String APP_CONF_PATH = "application.conf";
	private static final String APP_MODE_KEY = "app.mode";	

	private static final String HTTP_PROXY_HOST_KEY = "http.proxyHost";
	private static final String HTTP_PROXY_PORT_KEY = "http.proxyPort";
	private static final String HTTP_PROXY_USER_KEY = "http.proxyUser";
	private static final String HTTP_PROXY_PASSWORD_KEY = "http.proxyPassword";
	private static final long serialVersionUID = -8717482151558907451L;

	private static Logger logger;
	private static Properties instance;

}
