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
package com.stardog.ext.spring.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Adder;
import com.complexible.stardog.api.Connection;
import com.stardog.ext.spring.DataSource;

/**
 * Implementation of the Spring Batch ItemWriter interface that supports the SNARL API
 * 
 * @author Al Baker
 * @author Clark &amp; Parsia
 *
 */
public class SnarlItemWriter<T> implements ItemWriter<T> {

	final Logger log = LoggerFactory.getLogger(SnarlItemWriter.class);
	
	private DataSource dataSource;
	
	private BatchAdderCallback<T> callback;

}
