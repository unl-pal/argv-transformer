package pe.archety;

import com.google.common.util.concurrent.AbstractScheduledService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.UniqueFactory;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.security.SecureRandom;

import static pe.archety.ArchetypeConstants.DATA;
import static pe.archety.ArchetypeConstants.ACTION;

public class BatchWriterService extends AbstractScheduledService {

    private final static Logger logger = Logger.getLogger( BatchWriterService.class );
    private static final PathExpander LIKES_EXPANDER = PathExpanders.forTypeAndDirection( Relationships.LIKES, Direction.OUTGOING );
    private static final PathFinder<Path> ONE_HOP_LIKES_PATH = GraphAlgoFactory.shortestPath( LIKES_EXPANDER, 1 );
    private static final PathExpander HATES_EXPANDER = PathExpanders.forTypeAndDirection( Relationships.HATES, Direction.OUTGOING );
    private static final PathFinder<Path> ONE_HOP_HATES_PATH = GraphAlgoFactory.shortestPath( HATES_EXPANDER, 1 );
    private static final PathExpander KNOWS_EXPANDER = PathExpanders.forTypeAndDirection( Relationships.KNOWS, Direction.OUTGOING );
    private static final PathFinder<Path> ONE_HOP_KNOWS_PATH = GraphAlgoFactory.shortestPath( KNOWS_EXPANDER, 1 );
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private GraphDatabaseService graphDb;
    public LinkedBlockingQueue<HashMap<String, Object>> queue = new LinkedBlockingQueue<>();

    public final static BatchWriterService INSTANCE = new BatchWriterService();
}