package com.company.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.constant.AppScopeConstants;
import com.company.constant.Constants;
import com.company.service.captcha.Captcha;
import com.company.service.captcha.CaptchaContainer;
import com.company.service.captcha.captchaSaver.Saver;
import com.company.util.ImageGenerator;
import com.sun.image.codec.jpeg.JPEGCodec;

/**
 * HttpServlet creates and returns captcha.
 * 
 * @author Ivan_Tymchenko
 */
public class CaptchaCreator extends HttpServlet {
	
    private static final long serialVersionUID = -1761346889117186607L;
    
    private Saver captchaSaver;
}