package pe.archety;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.net.MediaType;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.HighlyAvailableGraphDatabaseFactory;
import pe.archety.handlers.admin.*;
import pe.archety.handlers.api.*;

public class ArchetypeServer {

    public static final String JSON_UTF8 = MediaType.JSON_UTF_8.toString();
    public static final String TEXT_PLAIN = MediaType.PLAIN_TEXT_UTF_8.toString();

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String STOREDIR = "/home/shroot/graphipedia/neo4j/data/graph.db";
    private static final String CONFIG = "/home/shroot/graphipedia/neo4j/conf/neo4j.properties";

    private static GraphDatabaseService graphDb;

    private static final BatchWriterService batchWriterService = BatchWriterService.INSTANCE;

    public static final Cache<String, Long> identityCache = CacheBuilder.newBuilder().maximumSize( 10_000_000 ).build();
    public static final Cache<String, Long> urlCache = CacheBuilder.newBuilder().maximumSize( 11_000_000 ).build();


}
