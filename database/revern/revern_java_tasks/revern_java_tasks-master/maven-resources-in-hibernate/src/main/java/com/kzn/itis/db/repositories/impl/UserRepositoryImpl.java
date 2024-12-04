package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.UserRepository;
import com.kzn.itis.db.util.SessionUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
}
