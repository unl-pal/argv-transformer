package com.arquivolivre.elastikjay.commons;

import com.arquivolivre.elastikyjay.annotations.Analyzer;
import com.arquivolivre.elastikyjay.annotations.Ignored;
import com.arquivolivre.elastikyjay.annotations.Index;
import com.arquivolivre.elastikyjay.annotations.Nested;
import com.arquivolivre.elastikyjay.annotations.NotAnalyzed;
import com.arquivolivre.elastikyjay.annotations.NotIndexed;
import static com.arquivolivre.elastikjay.commons.Types.isBasicType;
import static com.arquivolivre.elastikjay.commons.Types.isGeneric;
import com.google.gson.Gson;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;
import org.elasticsearch.common.settings.Settings;

/**
 *
 * @author Thiago da Silva Gonzaga <thiagosg@sjrp.unesp.br>
 */
public class IndexManagerImpl implements IndexManager {
    
    private final Client elasticSearchClient;
    private BulkRequestBuilder bulkRequest;
    private final Logger logger = Logger.getLogger(IndexManagerImpl.class);
    
}
