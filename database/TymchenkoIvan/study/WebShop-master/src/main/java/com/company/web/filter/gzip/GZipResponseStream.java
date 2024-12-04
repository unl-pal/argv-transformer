package com.company.web.filter.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.company.exception.FilterException;
import com.company.exception.Messages;
import com.company.util.ClassNameUtil;

public class GZipResponseStream extends ServletOutputStream {
	
	private static final Logger LOG = Logger.getLogger(ClassNameUtil.getCurrentClassName());
	
	private static final String CONTEXT_LENGHT = "Content-Length";
	private static final String CONTEXT_ENCODING = "Content-Encoding";
	private static final String GZIP = "gzip";
	
	protected ByteArrayOutputStream baos = null;
	protected GZIPOutputStream gzipOutputStream = null;
	protected boolean closed = false;
	protected HttpServletResponse response = null;
	protected ServletOutputStream output = null;
}
