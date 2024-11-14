/**
 * 
 */
package com.github.gm.hotconf.springconf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.StringUtils;

import com.github.gm.hotconf.HotConfigurableHooks;
import com.github.gm.hotconf.HotConfigurableProperties;
import com.github.gm.hotconf.annotations.HotConfigurableProperty;
import com.github.gm.hotconf.annotations.HotConfigurationHookAfter;
import com.github.gm.hotconf.annotations.HotConfigurationHookBefore;

/**
 * @author Gwendal Mousset
 *
 */
public final class HotConfigurablePropertyBeanProcessor implements BeanPostProcessor {

    /** Class logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HotConfigurablePropertyBeanProcessor.class);

    /** Configurable properties manager. */
    @Autowired
    private HotConfigurableProperties confProperties;

    /** Configurable hooks manager. */
    @Autowired
    private HotConfigurableHooks confHooks;
}
