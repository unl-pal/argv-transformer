package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.HibernateUtil;
import domain.School;

public class CreateSchoolService {

	private School school = new School();
	private List<School> schools = new ArrayList<School>();

	private SessionFactory sf = HibernateUtil.getSessionFactory();
}
