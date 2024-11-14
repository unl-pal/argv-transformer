package service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import util.HibernateUtil;
import domain.School;

public class RetrieveSchoolService {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	private List<School> schools = null;

	private SessionFactory sf = HibernateUtil.getSessionFactory();
}
