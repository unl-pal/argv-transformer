package fr.ac_versailles.crdp.apiscol.seek.representations;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.sun.jersey.api.Responses;

import fr.ac_versailles.crdp.apiscol.utils.LogUtility;

public class UnknownMediaTypeForResponseException extends
		WebApplicationException {
	private static Logger webApplicationExceptionLogger;
	{
		if (webApplicationExceptionLogger == null) {
			webApplicationExceptionLogger = LogUtility
					.createLogger(WebApplicationException.class
							.getCanonicalName());
			webApplicationExceptionLogger.info("Logger creation : "
					+ webApplicationExceptionLogger.getName());
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
