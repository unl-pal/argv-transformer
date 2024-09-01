package com.novadox.bigdata.gemfire.service;

import com.gemstone.gemfire.admin.SystemMembershipEvent;
import com.gemstone.gemfire.admin.SystemMembershipListener;
import com.gemstone.gemfire.cache.Region;
import com.novadox.bigdata.common.model.Constants;
import com.novadox.bigdata.common.model.Person;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;

@Service
@Configuration
public class RabbitReader implements SystemMembershipListener {
    private static Logger log = LoggerFactory.getLogger(RabbitReader.class);

//    @Autowired
//    private AmqpTemplate amqpTemplate;

    @Resource(name= Constants.PERSON_REGION)
    private Region<String, Person> personRegion;

    private SimpleMessageListenerContainer container;

    @Autowired
    private ConnectionFactory connectionFactory;
}
