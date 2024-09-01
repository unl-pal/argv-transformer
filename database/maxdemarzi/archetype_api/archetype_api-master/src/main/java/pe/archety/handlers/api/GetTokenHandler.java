package pe.archety.handlers.api;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IteratorUtil;
import pe.archety.Labels;

import org.apache.log4j.Logger;

import static pe.archety.ArchetypeServer.TEXT_PLAIN;

public class GetTokenHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger( GetTokenHandler.class.getName() );
    private static GraphDatabaseService graphDB;
}
