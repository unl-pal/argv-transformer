package pe.archety.handlers.admin;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.nio.ByteBuffer;
import static pe.archety.ArchetypeServer.TEXT_PLAIN;

public class HelloWorldHandler implements HttpHandler {
    private static final ByteBuffer buffer;
    private static final String MESSAGE = "Hello, World!";

    static {
        buffer = ByteBuffer.allocateDirect(MESSAGE.length());
        try {
            buffer.put(MESSAGE.getBytes("US-ASCII"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        buffer.flip();
    }
}