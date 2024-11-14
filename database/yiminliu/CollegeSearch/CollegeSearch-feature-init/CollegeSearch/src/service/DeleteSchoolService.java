package service;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.HibernateUtil;
import domain.School;

public class DeleteSchoolService {

	/*
	 * private School school = new School(); private Contact schoolContact = new
	 * Contact(); private Address schoolAddress = new Address(); private
	 * SchoolExpense schoolExpense = new SchoolExpense(); private SchoolFact
	 * schoolFact = new SchoolFact(); private SchoolRank schoolRank = new
	 * SchoolRank(); private SchoolRequirement schoolRequirement = new
	 * SchoolRequirement(); private SchoolStudentBody schoolStudentBody = new
	 * SchoolStudentBody();
	 */
	private SessionFactory sf = HibernateUtil.getSessionFactory();
}
