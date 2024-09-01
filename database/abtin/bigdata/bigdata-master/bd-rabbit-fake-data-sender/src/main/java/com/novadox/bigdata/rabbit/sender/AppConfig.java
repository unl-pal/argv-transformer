package com.novadox.bigdata.rabbit.sender;

import com.novadox.bigdata.common.model.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class AppConfig {

    @Value("${rabbitServerHost}")
    private String rabbitSererHost;

    @Value("${rabbitUserName}")
    private String rabbitUser;

    @Value("${rabbitPassword}")
    private String rabbitPassword;

    @Value("${rabbitVirtualHost}")
    private String rabbitVirtualHost;
}
