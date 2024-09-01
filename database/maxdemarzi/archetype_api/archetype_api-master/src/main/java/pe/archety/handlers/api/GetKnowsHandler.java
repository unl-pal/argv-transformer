package pe.archety.handlers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.neo4j.graphdb.*;
import pe.archety.ArchetypeConstants;
import pe.archety.ArchetypeServer;
import pe.archety.Labels;
import pe.archety.Relationships;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import static pe.archety.ArchetypeConstants.EMAIL_VALIDATOR;
import static pe.archety.ArchetypeConstants.PHONE_UTIL;

public class GetKnowsHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(GetKnowsHandler.class.getName());
    private static GraphDatabaseService graphDB;
    private static ObjectMapper objectMapper;
}