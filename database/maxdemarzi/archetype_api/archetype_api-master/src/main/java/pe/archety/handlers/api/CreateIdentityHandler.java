package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import pe.archety.*;


import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

import static pe.archety.ArchetypeConstants.EMAIL_VALIDATOR;
import static pe.archety.ArchetypeConstants.PHONE_UTIL;
import static pe.archety.ArchetypeConstants.BATCH_WRITER_SERVICE;

public class CreateIdentityHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(CreateIdentityHandler.class.getName());
    private static GraphDatabaseService graphDB;
    private static ObjectMapper objectMapper;

}