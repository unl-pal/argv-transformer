/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.web.websocketbot;

import javaeetutorial.web.websocketbot.encoders.UsersMessageEncoder;
import javaeetutorial.web.websocketbot.encoders.ChatMessageEncoder;
import javaeetutorial.web.websocketbot.encoders.InfoMessageEncoder;
import javaeetutorial.web.websocketbot.encoders.JoinMessageEncoder;
import javaeetutorial.web.websocketbot.messages.ChatMessage;
import javaeetutorial.web.websocketbot.messages.UsersMessage;
import javaeetutorial.web.websocketbot.messages.JoinMessage;
import javaeetutorial.web.websocketbot.messages.InfoMessage;
import javaeetutorial.web.websocketbot.decoders.MessageDecoder;
import javaeetutorial.web.websocketbot.messages.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/** 
 * https://docs.oracle.com/javaee/7/tutorial/websocket.htm#GKJIQ5
 * <p>
 * http://localhost:8080/websocketbot/
 * <p>
 *  For example, type the messages in bold and press enter to obtain responses similar to the following:
 *  <pre>
 *  [--Peter has joined the chat--]
	Duke: @Peter Hi there!!
	Peter: @Duke how are you?
	Duke: @Peter I'm doing great, thank you!
	Peter: @Duke when is your birthday?
	Duke: @Peter My birthday is on May 23rd. Thanks for asking!
 *  </pre>
 *  <p>
 * Websocket endpoint 
 * <p>
 * Implementa a sala de bate-papo
 * <p>
 * O endpoint WebSocket ( BotEndpoint ) � um ponto final anotado que realiza as seguintes fun��es:
	<p>
	Recebe mensagens de clientes
	<p>
	Mensagens para a frente para clientes
	<p>
	Mant�m uma lista de clientes ligados
	<p>
	Invoca a funcionalidade do agente bot
	
 * */
@ServerEndpoint(
        value = "/websocketbot",
        decoders = { MessageDecoder.class }, 
        encoders = { JoinMessageEncoder.class, ChatMessageEncoder.class, 
                     InfoMessageEncoder.class, UsersMessageEncoder.class }
        )
/* There is a BotEndpoint instance per connetion */
public class BotEndpoint {
    private static final Logger logger = Logger.getLogger("BotEndpoint");
    
    /* Bot functionality bean */
    @Inject
    private BotStockBean botstockbean;
    
    /* Executor service for asynchronous processing */
   // @Resource(lookup="java:comp/DefaultManagedExecutorService")
    private ManagedExecutorService executor = null;
}
