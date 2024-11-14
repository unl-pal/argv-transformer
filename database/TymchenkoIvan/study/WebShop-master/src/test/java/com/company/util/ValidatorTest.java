package com.company.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.company.exception.ValidationException;
import com.company.service.bean.RegistrationFormBean;
import com.company.util.Validator;

public class ValidatorTest {

	private static RegistrationFormBean nullBean = new RegistrationFormBean();
	private static RegistrationFormBean validBean = new RegistrationFormBean();
	private static RegistrationFormBean notValidBean = new RegistrationFormBean();
}
