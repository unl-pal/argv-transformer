package com.kzn.itis.db.util;

import org.hibernate.ejb.HibernatePersistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.util.HashMap;

public class SessionUtil {

    private static EntityManagerFactory entityManagerFactory;

    private static final ThreadLocal<EntityManager> sessionThreadLocal = new ThreadLocal<EntityManager>();

    private static final ThreadLocal<Integer> currentUserId = new ThreadLocal<Integer>();
}
