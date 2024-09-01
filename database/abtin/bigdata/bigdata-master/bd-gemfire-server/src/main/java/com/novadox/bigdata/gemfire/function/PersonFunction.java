package com.novadox.bigdata.gemfire.function;


import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.execute.FunctionContext;
import com.gemstone.gemfire.cache.execute.RegionFunctionContext;
import com.novadox.bigdata.common.model.Constants;
import com.novadox.bigdata.common.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.gemfire.function.annotation.GemfireFunction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonFunction {
    private static Logger log = LoggerFactory.getLogger(PersonFunction.class);

    @Resource(name= Constants.PERSON_REGION)
    private Region<String, Person> personRegion;

}
