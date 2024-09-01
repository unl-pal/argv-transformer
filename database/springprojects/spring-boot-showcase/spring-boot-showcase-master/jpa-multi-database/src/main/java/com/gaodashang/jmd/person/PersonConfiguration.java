/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gaodashang.jmd.person;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.gaodashang.jmd.AtomikosJtaPlatform;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
//@EnableJpaRepositories(basePackageClasses = Person.class, entityManagerFactoryRef = "personEntityManagerFactory", transactionManagerRef = "personTransactionManager")
@EnableJpaRepositories(basePackageClasses = Person.class, entityManagerFactoryRef = "personEntityManagerFactory")
@EnableConfigurationProperties(PersonDatasourceProperties.class)
public class PersonConfiguration {

	@Autowired
	private PersonDatasourceProperties personDatasourceProperties;

	@Autowired
	private JpaProperties jpaProperties;

	/*
	@Bean(name = "personTransactionManager")
	@Primary
	public PlatformTransactionManager personTransactionManager(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(personEntityManagerFactory(builder).getObject());
	}
	*/

}
