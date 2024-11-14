package org.rakam.server.http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SHttpServer
        implements SHttpServerMBean
{
    private final HttpServer httpServer;

    public static class Holder {
        public long count;
        public long sum;
    }
}
