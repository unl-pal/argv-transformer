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
package com.yahoo.ycsb.generator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.yahoo.ycsb.Utils;
import com.yahoo.ycsb.generator.IntegerGenerator;

/**
 * Generate integers according to a histogram distribution.  The histogram
 * buckets are of width one, but the values are multiplied by a block size.
 * Therefore, instead of drawing sizes uniformly at random within each
 * bucket, we always draw the largest value in the current bucket, so the value
 * drawn is always a multiple of block_size.
 * 
 * The minimum value this distribution returns is block_size (not zero).
 * 
 * Modified Nov 19 2010 by sears
 * 
 * @author snjones
 *
 */
public class HistogramGenerator extends IntegerGenerator {

	long block_size;
	long[] buckets;
	long area;
	long weighted_area = 0;
	double mean_size = 0;
	
	@Override
	public int nextInt() {
		int number = Utils.random().nextInt((int)area);
		int i;
		
		for(i = 0; i < (buckets.length - 1); i++){
			number -= buckets[i];
			if(number <= 0){
				return (int)((i+1)*block_size);
			}
		}
		
		return (int)(i * block_size);
	}
}
