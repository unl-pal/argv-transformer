package domain;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class BestMajorsTest extends TestCase {

	private BestMajors bestmajors;

	private SessionFactory sf;
	Session session = null;
	Transaction tx = null;
}