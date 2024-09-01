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
package org.tamilunicodeconverter.converter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.tamilunicodeconverter.converter.BaminiConverter;
import org.tamilunicodeconverter.extractor.ContentExtractor;

/**
 * 
 * @author James Selvakumar
 */
public class BaminiConverterTest
{
	public final String PPT_DIR = "src/test/resources/ppt";
	private BaminiConverter baminiConverter = new BaminiConverter();

}
