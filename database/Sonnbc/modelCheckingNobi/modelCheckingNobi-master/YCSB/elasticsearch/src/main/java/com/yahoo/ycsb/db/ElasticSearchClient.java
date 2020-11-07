package com.yahoo.ycsb.db;

import com.yahoo.ycsb.ByteIterator;
import com.yahoo.ycsb.DB;
import com.yahoo.ycsb.DBException;
import com.yahoo.ycsb.StringByteIterator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import static org.elasticsearch.common.settings.ImmutableSettings.*;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.*;
import org.elasticsearch.search.SearchHit;

/**
 * ElasticSearch client for YCSB framework.
 *
 * <p>Default properties to set:</p> <ul> <li>es.cluster.name = es.ycsb.cluster
 * <li>es.client = true <li>es.index.key = es.ycsb</ul>
 *
 * @author Sharmarke Aden
 *
 */
public class ElasticSearchClient extends DB {

    public static final String DEFAULT_CLUSTER_NAME = "es.ycsb.cluster";
    public static final String DEFAULT_INDEX_KEY = "es.ycsb";
    private Node node;
    private Client client;
    private String indexKey;
}
