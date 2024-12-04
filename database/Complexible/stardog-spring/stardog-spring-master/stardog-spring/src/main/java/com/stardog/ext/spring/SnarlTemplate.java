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
import com.complexible.stardog.api.*;
import com.stardog.ext.spring.utils.TypeConverter;
import com.google.common.collect.ImmutableSet;
import com.stardog.stark.*;
import com.stardog.stark.query.QueryExecutionFailure;
import com.stardog.stark.query.GraphQueryResult;
import com.stardog.stark.query.SelectQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SnarlTemplate
 * 
 * Template design pattern, similar to Spring's jdbcTemplate, jmsTemplate, etc
 * 
 * Moves all boiler plate connection setup, transactions, and resource close outs
 * into the template
 * 
 * @author Clark and Parsia, LLC
 * @author Al Baker
 *
 */
public class SnarlTemplate {

	final Logger log = LoggerFactory.getLogger(SnarlTemplate.class);

	private DataSource dataSource;
}
