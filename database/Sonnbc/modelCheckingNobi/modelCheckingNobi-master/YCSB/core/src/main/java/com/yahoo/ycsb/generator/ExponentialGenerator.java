/**                                                                                                                                                                                
 * Copyright (c) 2011 Yahoo! Inc. All rights reserved.                                                                                                                             
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

package com.yahoo.ycsb.generator;

import java.util.Random;

import com.yahoo.ycsb.Utils;

/**
 * A generator of an exponential distribution. It produces a sequence
 * of time intervals (integers) according to an exponential
 * distribution.  Smaller intervals are more frequent than larger
 * ones, and there is no bound on the length of an interval.  When you
 * construct an instance of this class, you specify a parameter gamma,
 * which corresponds to the rate at which events occur.
 * Alternatively, 1/gamma is the average length of an interval.
 */
public class ExponentialGenerator extends IntegerGenerator
{
    // What percentage of the readings should be within the most recent exponential.frac portion of the dataset?
    public static final String EXPONENTIAL_PERCENTILE_PROPERTY="exponential.percentile";
    public static final String EXPONENTIAL_PERCENTILE_DEFAULT="95";

    // What fraction of the dataset should be accessed exponential.percentile of the time?
    public static final String EXPONENTIAL_FRAC_PROPERTY = "exponential.frac";
    public static final String EXPONENTIAL_FRAC_DEFAULT  = "0.8571428571";  // 1/7

	/**
	 * The exponential constant to use.
	 */
	double _gamma;
}
