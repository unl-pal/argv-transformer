package com.company.web.filter.gzip;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

import com.company.exception.FilterException;
import com.company.exception.Messages;
import com.company.util.ClassNameUtil;

public class GZipResponseWrapper extends HttpServletResponseWrapper {
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private static final String ENCODING = "UTF-8";
	
	protected HttpServletResponse response = null;
	protected ServletOutputStream outputStream = null;
	protected PrintWriter printWriter = null;
}