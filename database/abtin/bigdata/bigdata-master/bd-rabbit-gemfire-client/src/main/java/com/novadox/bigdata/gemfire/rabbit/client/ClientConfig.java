package com.novadox.bigdata.gemfire.rabbit.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;

@ImportResource("local/client-cache.xml")
@EnableGemfireFunctionExecutions(basePackages="com.novadox.bigdata.common.api" )
@Configuration
public class ClientConfig {
}
