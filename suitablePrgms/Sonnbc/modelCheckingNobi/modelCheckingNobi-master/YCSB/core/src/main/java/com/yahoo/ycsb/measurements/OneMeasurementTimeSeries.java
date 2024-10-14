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
import java.util.Vector;

import com.yahoo.ycsb.measurements.exporter.MeasurementsExporter;

class SeriesUnit
{
	public long time;
	public double average; 
}

/**
 * A time series measurement of a metric, such as READ LATENCY.
 */
public class OneMeasurementTimeSeries extends OneMeasurement 
{
	/**
	 * Granularity for time series; measurements will be averaged in chunks of this granularity. Units are milliseconds.
	 */
	public static final String GRANULARITY="timeseries.granularity";
	
	public static final String GRANULARITY_DEFAULT="1000";
	
	int _granularity;
	Vector<SeriesUnit> _measurements;
	
	long start=-1;
	long currentunit=-1;
	int count=0;
	int sum=0;
	int operations=0;
	long totallatency=0;
	
	//keep a windowed version of these stats for printing status
	int windowoperations=0;
	long windowtotallatency=0;
	
	int min=-1;
	int max=-1;

	private HashMap<Integer, int[]> returncodes;
	
	@Override
	public void measure(int latency) 
	{
		checkEndOfUnit(false);
		
		count++;
		sum+=latency;
		totallatency+=latency;
		operations++;
		windowoperations++;
		windowtotallatency+=latency;
		
		if (latency>max)
		{
			max=latency;
		}
		
		if ( (latency<min) || (min<0) )
		{
			min=latency;
		}
	}

}
