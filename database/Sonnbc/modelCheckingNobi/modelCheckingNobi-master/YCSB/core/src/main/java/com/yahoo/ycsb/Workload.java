/**                                                                                                                                                                                
 * Copyright (c) 2010 Yahoo! Inc. All rights reserved.                                                                                                                             
 *                                                                                                                                                                                 
 * Licensed under the Apache License, Version 2.0 (the "License"); you                                                                                                             
 * may not use this file except in compliance with the License. You                                                                                                                
 * may obtain a copy of the License at                                                                                                                                             
 *                                                                                                                                                                                 
 * http://www.apache.org/licenses/LICENSE-2.0                                                                                                                                      
 *                                                                                                                                                                                 
 * Unless required by applicable law or agreed to in writing, software                                                                                                             
 * distributed under the License is distributed on an "AS IS" BASIS,                                                                                                               
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or                                                                                                                 
 * implied. See the License for the specific language governing                                                                                                                    
 * permissions and limitations under the License. See accompanying                                                                                                                 
 * LICENSE file.                                                                                                                                                                   
 */

package com.yahoo.ycsb;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * One experiment scenario. One object of this type will
 * be instantiated and shared among all client threads. This class
 * should be constructed using a no-argument constructor, so we can
 * load it dynamically. Any argument-based initialization should be
 * done by init().
 * 
 * If you extend this class, you should support the "insertstart" property. This 
 * allows the load phase to proceed from multiple clients on different machines, in case
 * the client is the bottleneck. For example, if we want to load 1 million records from
 * 2 machines, the first machine should have insertstart=0 and the second insertstart=500000. Additionally,
 * the "insertcount" property, which is interpreted by Client, can be used to tell each instance of the
 * client how many inserts to do. In the example above, both clients should have insertcount=500000.
 */
public abstract class Workload
{
	public static final String INSERT_START_PROPERTY="insertstart";
	
	public static final String INSERT_START_PROPERTY_DEFAULT="0";
	
	private volatile AtomicBoolean stopRequested = new AtomicBoolean(false);
}
