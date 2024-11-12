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

import java.util.Random;

import com.yahoo.ycsb.Utils;

/**
 * A generator of a zipfian distribution. It produces a sequence of items, such that some items are more popular than others, according
 * to a zipfian distribution. When you construct an instance of this class, you specify the number of items in the set to draw from, either
 * by specifying an itemcount (so that the sequence is of items from 0 to itemcount-1) or by specifying a min and a max (so that the sequence is of 
 * items from min to max inclusive). After you construct the instance, you can change the number of items by calling nextInt(itemcount) or nextLong(itemcount).
 * 
 * Note that the popular items will be clustered together, e.g. item 0 is the most popular, item 1 the second most popular, and so on (or min is the most 
 * popular, min+1 the next most popular, etc.) If you don't want this clustering, and instead want the popular items scattered throughout the 
 * item space, then use ScrambledZipfianGenerator instead.
 * 
 * Be aware: initializing this generator may take a long time if there are lots of items to choose from (e.g. over a minute
 * for 100 million objects). This is because certain mathematical values need to be computed to properly generate a zipfian skew, and one of those
 * values (zeta) is a sum sequence from 1 to n, where n is the itemcount. Note that if you increase the number of items in the set, we can compute
 * a new zeta incrementally, so it should be fast unless you have added millions of items. However, if you decrease the number of items, we recompute
 * zeta from scratch, so this can take a long time. 
 *
 * The algorithm used here is from "Quickly Generating Billion-Record Synthetic Databases", Jim Gray et al, SIGMOD 1994.
 */
public class ZipfianGenerator extends IntegerGenerator
{     
	public static final double ZIPFIAN_CONSTANT=0.99;

	/**
	 * Number of items.
	 */
	long items;
	
	/**
	 * Min item to generate.
	 */
	long base;
	
	/**
	 * The zipfian constant to use.
	 */
	double zipfianconstant;
	
	/**
	 * Computed parameters for generating the distribution.
	 */
	double alpha,zetan,eta,theta,zeta2theta;
	
	/**
	 * The number of items used to compute zetan the last time.
	 */
	long countforzeta;
	
	/**
	 * Flag to prevent problems. If you increase the number of items the zipfian generator is allowed to choose from, this code will incrementally compute a new zeta
	 * value for the larger itemcount. However, if you decrease the number of items, the code computes zeta from scratch; this is expensive for large itemsets.
	 * Usually this is not intentional; e.g. one thread thinks the number of items is 1001 and calls "nextLong()" with that item count; then another thread who thinks the 
	 * number of items is 1000 calls nextLong() with itemcount=1000 triggering the expensive recomputation. (It is expensive for 100 million items, not really for 1000 items.) Why
	 * did the second thread think there were only 1000 items? maybe it read the item count before the first thread incremented it. So this flag allows you to say if you really do
	 * want that recomputation. If true, then the code will recompute zeta if the itemcount goes down. If false, the code will assume itemcount only goes up, and never recompute. 
	 */
	boolean allowitemcountdecrease=false;
}
