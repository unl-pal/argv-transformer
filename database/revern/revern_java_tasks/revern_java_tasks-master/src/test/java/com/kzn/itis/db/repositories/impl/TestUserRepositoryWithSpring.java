package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.UserRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;


/**
 *
 */
public class TestUserRepositoryWithSpring {

//    @BeforeClass
//    public static void init() {
//        AbstractApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
//        DatabaseConfiguration config = (DatabaseConfiguration)context.getBean("databaseConfiguration");
//    }
//
//    @Test
//    public void testCreate() throws SQLException {
//        UserRepository userRepository = new UserRepositoryImpl();
//
//        long count = userRepository.getCount();
//
//        User user = new User();
//        user.setAge(25);
//        user.setFirstname("Sherlock");
//        user.setLastname("Holmes");
//
//        userRepository.addUser(user);
//
//        long newCount = userRepository.getCount();
//
//        Assert.assertEquals(count + 1, newCount);
//    }
}
