package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import pe.archety.*;

import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static pe.archety.ArchetypeConstants.URLPREFIX;
import static pe.archety.ArchetypeConstants.HTTP_CLIENT;
import static pe.archety.ArchetypeConstants.BATCH_WRITER_SERVICE;

public class CreatePageHandler implements HttpHandler {

    private static GraphDatabaseService graphDB;
    private static ObjectMapper objectMapper;
}


