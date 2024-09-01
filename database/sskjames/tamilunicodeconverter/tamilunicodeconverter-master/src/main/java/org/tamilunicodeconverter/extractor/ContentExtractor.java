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
package org.tamilunicodeconverter.extractor;

import java.io.File;
import org.apache.commons.lang.UnhandledException;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.utils.ParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author James Selvakumar
 */
public class ContentExtractor
{
    private static final Logger logger = LoggerFactory.getLogger(ContentExtractor.class);
    private static ContentExtractor instance;
}
