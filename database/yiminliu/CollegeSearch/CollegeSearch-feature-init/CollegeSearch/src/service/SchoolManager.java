package service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import domain.Address;
import domain.School;
import domain.SchoolExpense;
import domain.SchoolFact;

import util.HibernateUtil;

public class SchoolManager {

	private SessionFactory sf = HibernateUtil.getSessionFactory();
}
