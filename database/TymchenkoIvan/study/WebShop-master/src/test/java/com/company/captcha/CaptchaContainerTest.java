package com.company.captcha;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.company.service.captcha.CaptchaContainer;

public class CaptchaContainerTest {
	
	private static int captchaCode = 12345;
	private static Long timeout = 1000000L;
	private static Long captchaId;
	private static Long captchaOldId;
	private static Long captchaIdIncorrect = 0L;
}
