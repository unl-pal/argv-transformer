/**
 * 
 */
package com.github.gm.hotconf.test.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.github.gm.hotconf.HotConfBootstrap;
import com.github.gm.hotconf.annotations.EnableHotConfiguration;

/**
 * @author Gwendal Mousset
 *
 */
@Configuration
@ComponentScan(basePackages = "com.github.gm.hotconf.test.web.service")
@PropertySource({"classpath:config.properties"})
@EnableHotConfiguration
public class AppConfig {	
}
