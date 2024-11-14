/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package javaeetutorial.web.dukeetf2;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/** 
 * WebSocket version of the dukeetf example demonstra como usar um endpoint WebSocket 
 * para fornecer atualizacoes de dados para clientes web. O exemplo se assemelha 
 * a um servico que fornece atualizacoes periodicas sobre o preco e volume de 
 * negociacao de um fundo negociado eletronicamente (ETF), armazenando todas as 
 * sessoes conectadas em uma fila, e fornece um metodo que o bean
 * corporativo chama quando houver novas informacoes disponiveis para enviar.
 * <p>
 * http://localhost:8080/dukeetf2/
 * */
@ServerEndpoint("/dukeetf")
public class ETFEndpoint {
	
    private static final Logger logger = Logger.getLogger("ETFEndpoint");
    /* Queue for all open WebSocket sessions */
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    
    /* PriceVolumeBean calls this method to send updates */

    
}
