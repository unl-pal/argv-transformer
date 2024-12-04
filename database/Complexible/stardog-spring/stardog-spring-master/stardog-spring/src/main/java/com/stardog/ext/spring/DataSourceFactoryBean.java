/*
* Copyright (c) the original authors
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.stardog.ext.spring;

import com.complexible.common.base.Pair;
import com.complexible.stardog.api.ConnectionCredentials;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.ConnectionPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * StardogConnectionFactoryBean
 * 
 * This class implements the Spring interfaces for FactoryBean for a DataSource,
 * InitializingBean, and DisposableBean, so it is a fully Spring-aware factory
 * 
 * The objective is to configure one of these per Spring application context, and be
 * able to reference DataSource objects in the SnarlTemplate, so a SnarlTemplate always
 * gets a connection from the pool (via DataSource wrapper) injected in to it.
 * 
 * Configuration for this object matches both the parameters in ConnectionConfiguration and
 * ConnectionPoolConfiguration, and then inspects what has been injected to create the 
 * connection pool.  
 * 
 * @author Clark and Parsia, LLC
 * @author Al Baker
 *
 */
public class DataSourceFactoryBean implements FactoryBean<DataSource>, InitializingBean, DisposableBean {

	final Logger log = LoggerFactory.getLogger(DataSourceFactoryBean.class);
	
	/**
	 * Properties used by the ConnectionConfig
	 */
	private String url;
	
	private String username;
	
	private String password;

	private boolean reasoningType = false;
	
	private String to;

	private Supplier<ConnectionCredentials> supplier;
	
	private Properties connectionProperties;
	
	/**
	 * Properties used by the ConnectionPoolConfig
	 * 
	 * TimeUnits default to miliseconds, but can be configured in Spring
	 * 
	 */
	private long blockCapacityTime = 900;
	
	private TimeUnit blockCapacityTimeUnit = TimeUnit.SECONDS;
	
	private long expirationTime = 300;
	
	private TimeUnit expirationTimeUnit = TimeUnit.SECONDS;
	
	private boolean failAtCapacity = false;
	
	private boolean growAtCapacity = true;
	
	private int maxIdle = 100;
	
	private int maxPool = 200;
	
	private int minPool = 10;
	
	private boolean noExpiration = false;

    private Provider provider;
	
	
	/**
	 * Private references to the pools and configurations that get
	 *  constructed - see afterPropertiesSet()
	 */
	
	private DataSource dataSource;
}
