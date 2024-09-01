package com.gaodashang.demo.snake;

import java.awt.Color;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * comments.
 *
 * @author eva
 */
public class SnakeWebSocketHandler extends TextWebSocketHandler {
    public static final int PLAYFIELD_WIDTH = 640;
    public static final int PLAYFIELD_HEIGHT = 480;
    public static final int GRID_SIZE = 10;

    private static final AtomicInteger snakeIds = new AtomicInteger(0);
    private static final Random random = new Random();

    private final int id;
    private Snake snake;
}
