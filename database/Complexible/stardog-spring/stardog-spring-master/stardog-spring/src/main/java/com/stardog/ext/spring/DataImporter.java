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

import java.io.IOException;
import java.util.List;

import com.stardog.stark.io.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;

/**
 * DataImporter
 * 
 * Given the support in Stardog to add/remove data in bulk via a stream,
 * this class provides a Spring Resource aware 
 * 
 * @author Clark and Parsia, LLC
 * @author Al Baker
 *
 */
public class DataImporter implements InitializingBean {

	final Logger log = LoggerFactory.getLogger(DataImporter.class);
	
	private SnarlTemplate snarlTemplate;

	private List<Resource> inputFiles;
	
	private RDFFormat format;
	
	
	
	
}
