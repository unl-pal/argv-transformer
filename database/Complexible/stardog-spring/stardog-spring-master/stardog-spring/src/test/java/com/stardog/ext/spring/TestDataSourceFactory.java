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

import com.complexible.stardog.Contexts;
import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.Getter;
import com.complexible.stardog.api.Remover;
import com.complexible.stardog.api.admin.AdminConnection;
import com.stardog.ext.spring.mapper.SimpleRowMapper;
import com.stardog.ext.spring.mapper.SingleMapper;
import com.google.common.collect.ImmutableSet;
import com.stardog.stark.*;
import com.stardog.stark.io.RDFFormats;
import com.stardog.stark.query.BindingSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test cases for the StardogConnectionFactoryBean
 * 
 * Uses test-applicationContext in src/test/resources
 * 
 * @author Clark and Parsia, LLC
 * @author Al Baker
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-applicationContext.xml"})
public class TestDataSourceFactory  {

	private static Logger log = LoggerFactory.getLogger(TestDataSourceFactory.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
    private ApplicationContext applicationContext;

	@Autowired
	private SnarlTemplate snarlTemplate;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
}

