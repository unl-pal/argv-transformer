package domain;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddressTest extends TestCase {

	// private SessionFactory sf;
	Session session = null;
	Transaction tx = null;

	static Address address;
}