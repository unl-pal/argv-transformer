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

package com.yahoo.ycsb.measurements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Properties;

import com.yahoo.ycsb.measurements.exporter.MeasurementsExporter;


/**
 * Take measurements and maintain a histogram of a given metric, such as READ LATENCY.
 * 
 * @author cooperb
 *
 */
public class OneMeasurementHistogram extends OneMeasurement
{
	public static final String BUCKETS="histogram.buckets";
	public static final String BUCKETS_DEFAULT="1000";

	int _buckets;
	int[] histogram;
	int histogramoverflow;
	int operations;
	long totallatency;
	
	//keep a windowed version of these stats for printing status
	int windowoperations;
	long windowtotallatency;
	
	int min;
	int max;
	HashMap<Integer,int[]> returncodes;

	/* (non-Javadoc)
	 * @see com.yahoo.ycsb.OneMeasurement#measure(int)
	 */
	public synchronized void measure(int latency)
	{
		if (latency/1000>=_buckets)
		{
			histogramoverflow++;
		}
		else
		{
			histogram[latency/1000]++;
		}
		operations++;
		totallatency+=latency;
		windowoperations++;
		windowtotallatency+=latency;

		if ( (min<0) || (latency<min) )
		{
			min=latency;
		}

		if ( (max<0) || (latency>max) )
		{
			max=latency;
		}
	}

}
