package com.kzn.itis.db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class DatabaseConfiguration {

    @Value("${hibernate.db.url}")
    private String dbUrl;

    @Value("${hibernate.db.user}")
    private String dbUser;

    @Value("${hibernate.db.password}")
    private String dbPassword;

    @Value("${hibernate.hbm2ddl.auto.init-param}")
    private String dbHbm2ddl;
}
