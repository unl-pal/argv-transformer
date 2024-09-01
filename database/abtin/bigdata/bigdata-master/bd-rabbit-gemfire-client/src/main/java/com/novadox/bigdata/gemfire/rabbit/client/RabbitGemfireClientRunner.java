package com.novadox.bigdata.gemfire.rabbit.client;

import com.novadox.bigdata.common.model.Constants;
import com.novadox.bigdata.common.api.StorePersonFunctionApi;
import com.novadox.bigdata.common.model.Person;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabbitGemfireClientRunner {
    private static Logger log = LoggerFactory.getLogger(RabbitGemfireClientRunner.class);
    private ApplicationContext context = new AnnotationConfigApplicationContext(ClientConfig.class);
    private List<RabbitReader> spawnedReaders;
    private Channel channel;
    private Connection connection;

    class RabbitReader implements Runnable {
        private StorePersonFunctionApi api;
        private boolean isRunning;
        private QueueingConsumer consumer;
    }
}
