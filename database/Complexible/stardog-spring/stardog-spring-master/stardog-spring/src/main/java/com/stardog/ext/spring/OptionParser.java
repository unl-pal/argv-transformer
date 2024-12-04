//Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
//For more information about licensing and copyright of this software, please contact
//inquiries@clarkparsia.com or visit http://stardog.com

package com.stardog.ext.spring;

import java.util.List;
import java.util.Map;

import com.complexible.common.base.Option;
import com.complexible.common.base.Options;
import com.complexible.common.base.Pair;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

/**
* A registry of options that transforms arbitrary objects into the kind of object the option handles via a transforming function. 
* 
* @author Hector Perez-Urbina
* @version 2.0
*
*/
class OptionParser {

	private final Map<Option<?>, Function<Object, ?>> mOptionsMap;
	
	
}