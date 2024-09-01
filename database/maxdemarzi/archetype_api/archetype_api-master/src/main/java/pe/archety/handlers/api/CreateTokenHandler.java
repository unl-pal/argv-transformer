package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IteratorUtil;
import pe.archety.ArchetypeConstants;
import pe.archety.ArchetypeServer;
import pe.archety.BatchWriterServiceAction;
import pe.archety.Labels;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

import static pe.archety.ArchetypeConstants.BATCH_WRITER_SERVICE;
import static pe.archety.ArchetypeConstants.EMAIL_VALIDATOR;

public class CreateTokenHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(CreateTokenHandler.class.getName());
    private static GraphDatabaseService graphDB;
    private static ObjectMapper objectMapper;

}
