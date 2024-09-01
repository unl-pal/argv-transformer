package com.arquivolivre.elastikjay.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
public class Configuration {
    
    protected TransportClientFactory transportFactory;
    private final String CLUSTER_NAME_KEY = "elasticsearch.cluster.name";
    private final String CLUSTER_NODES_KEY = "elasticsearch.cluster.nodes";
    private final String CLUSTER_PORTS_KEY = "elasticsearch.cluster.ports";
    private final String DEFAULT_PORT_KEY = "elasticsearch.port";
    private final String DEFAULT_CLUSTER_NODES_KEY = "elasticsearch.nodes";
    private final String DEFAULT_NODE_KEY = "elasticsearch.node";
    private Properties properties;
    private final Logger logger = Logger.getLogger(Configuration.class);
    
}
