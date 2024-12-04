/**
 * 
 */
package com.github.gm.hotconf.test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ReflectionUtils;

import com.github.gm.hotconf.Errors;
import com.github.gm.hotconf.HotConfigurableProperties;
import com.github.gm.hotconf.test.web.config.AppConfig;
import com.github.gm.hotconf.test.web.config.WebMvcConfig;
import com.github.gm.hotconf.test.web.service.TestingService;
import com.github.gm.hotconf.test.web.service.TestingService2;

/**
 * @author Gwendal Mousset
 *
 */
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = AppConfig.class), @ContextConfiguration(classes = WebMvcConfig.class)})
@RunWith(SpringJUnit4ClassRunner.class)
public class HotConfigurationTest {

  /** */
  private static final String TESTING_SERVICE_IMPL_STRING_PROP = "testingServiceImpl.stringProp";

  /** */
  private static final String FLOAT_OBJ = "float.obj";

  /** */
  private static final String FLOAT_PRIMITIVE = "float.primitive";

  /** */
  private static final String DOUBLE_OBJ = "double.obj";

  /** */
  private static final String DOUBLE_PRIMITIVE = "double.primitive";

  /** */
  private static final String LONG_OBJ = "long.obj";

  /** */
  private static final String LONG_PRIMITIVE = "long.primitive";

  /** */
  private static final String INT_MAX = "int.max";

  /** */
  private static final String INT_MIN = "int.min";

  /** */
  private static final Logger LOGGER = LoggerFactory.getLogger(HotConfigurationTest.class);

  /** */
  @Autowired
  private TestingService testingService;
  
  /** */
  @Autowired
  private TestingService2 testingService2;

  /** */
  @Autowired
  private HotConfigurableProperties hotConfProps;

  /** */
  private ArrayList<String> unsupportedType;
}
