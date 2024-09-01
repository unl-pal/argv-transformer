/*
 * Licensed to the http://code.google.com/p/tamilunicodeconverter under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tamilunicodeconverter.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author James Selvakumar
 *
 */
public class BaminiUnicodeMap
{
	public static final String MAP_FILE = "bamini.txt";
	private static final Logger logger = LoggerFactory
			.getLogger(BaminiUnicodeMap.class);
	private static Map<String, String> map = new LinkedHashMap<String, String>();

	static {
		URL url = ClassLoader.getSystemClassLoader().getResource(MAP_FILE);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					FileUtils.toFile(url)));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if(line.contains("=")) {
					String[] tokens = StringUtils.split(line, "=");
					if(tokens.length == 2) {
						map.put(tokens[0], tokens[1]);
					}
				}
			}
			
		} catch (Exception ex) {
			logger.error(
					"Error occured while reading the file " + url.getFile(), ex);
		}
	}
}
