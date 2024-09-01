package com.gaodashang.demo;

import com.gaodashang.demo.client.GreetingService;
import com.gaodashang.demo.client.SimpleGreetingService;
import com.gaodashang.demo.echo.EchoService;
import com.gaodashang.demo.echo.EchoServiceImpl;
import com.gaodashang.demo.echo.EchoWebSocketHandler;
import com.gaodashang.demo.reverse.ReverseWebSocketEndpoint;
import com.gaodashang.demo.snake.SnakeWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * comments.
 *
 * @author eva
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {


}
