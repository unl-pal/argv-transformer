package domain;

import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class SchoolTest extends TestCase {

	static final String name = "University of Pessylvinia";

	private SessionFactory sf;
	Session session = null;
	Transaction tx = null;
	private School aSchool = new School();
	Address address;
	SchoolExpense expense;
	SchoolFact fact = new SchoolFact();
}
