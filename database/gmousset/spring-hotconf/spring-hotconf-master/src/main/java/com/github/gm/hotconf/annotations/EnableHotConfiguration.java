/**
 * 
 */
package com.github.gm.hotconf.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.github.gm.hotconf.springconf.HotConfigurablePropertiesBeanDefinitionRegistrar;

/**
 * Add this annotation on @Configuration class. Active and setup HotConf.
 * 
 * @author Gwendal Mousset
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HotConfigurablePropertiesBeanDefinitionRegistrar.class)
public @interface EnableHotConfiguration {

}
